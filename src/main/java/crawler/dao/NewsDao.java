package crawler.dao;

import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;

/**
 * 数据库访问.
 */
public class NewsDao {

    private static final Object LOCK = new Object();

    /**
     * 新闻添加到库，添加前查询同名标题.
     *
     * @param news 新闻实体
     */
    public synchronized void addNews(News news) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            if (sqlSession.selectList("findBy_Title",  news.getTitle()).size() == 0) {
                sqlSession.insert("News.news_add", news);
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MybatisUtil.closeSqlSession();
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
            sqlSession.insert("News.links_to_be_solved_add", link);
            sqlSession.commit();
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
     * 查询链接表是否有该链接.
     *
     * @param link 要查询的链接
     * @return 含有该链接就返回true
     */
    public static boolean findByLink(String link) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            synchronized (LOCK) {
                List<String> result = sqlSession.selectList("findBy_Link", link);
                return result.size() == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    public List<News> findAll() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            return sqlSession.selectList("News.all_news");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }


}
