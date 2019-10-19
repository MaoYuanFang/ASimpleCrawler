package crawler.dao;

import org.apache.http.HttpHost;
import org.apache.ibatis.session.SqlSession;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库访问.
 */
public class NewsDao {

    private static final Object LOCK = new Object();
    private static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9201, "http")));

    /**
     * 新闻添加到库，添加前查询同名标题,添加成功后再添加到搜索引擎库.
     *
     * @param news 新闻
     */
    public synchronized void addNews(News news) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            if (sqlSession.selectList("findBy_Title", news.getTitle()).size() == 0) {
                sqlSession.insert("News.news_add", news);
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MybatisUtil.closeSqlSession();
            addToElasticsearch(news);
        }
    }

    /**
     * 添加到要处理的表.
     *
     * @param link 添加的链接
     */
    public void add_link_to_be_solved(String link) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            synchronized (LOCK) {
                if (sqlSession.selectList("findBy_Link", link).size() == 0) {
                    sqlSession.insert("News.links_to_be_solved_add", link);
                    sqlSession.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }


    /**
     * 从链接表拿出要处理的链接，然后删除，和查询操作同步.
     *
     * @return 返回拿到的链接
     */
    public static String processLink() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            synchronized (LOCK) {
                String link = sqlSession.selectOne("News.get_link");
                if (link != null) {
                    sqlSession.delete("News.delete_link");
                    sqlSession.insert("News.links_solved_add", link);
                    sqlSession.commit();
                }
                return link;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            throw e;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    /**
     * 添加到搜索引擎库.
     *
     * @param news 新闻
     */
    private void addToElasticsearch(News news) {
        IndexRequest request = new IndexRequest("news");
        Map<String, Object> data = new HashMap<>();
        data.put("id", news.getId());
        data.put("title", news.getTitle());
        String content = news.getContent();
        data.put("content", content.length() > 20 ? content.substring(0, 20) : content);
        data.put("date", new Date(news.getDate().getTime()));
        data.put("url", news.getUrl());
        request.source(data, XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
