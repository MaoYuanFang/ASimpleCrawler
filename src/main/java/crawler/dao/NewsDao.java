package crawler.dao;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 数据库访问.
 */
public class NewsDao {


    /**
     * 新闻添加到库.
     *
     * @param news 储存新闻的对象
     * @param url  根据url匹配表
     */
    public void addNews(News news, String url) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            if (url.matches("(.*)sports.sina(.*)")
                    || url.matches("(.*)nba.sina(.*)")) {
                sqlSession.insert("News.sports_news_add", news);
            } else if (url.matches("(.*)cj.sina(.*)")
                    || url.matches("(.*)finance.sina(.*)")) {
                sqlSession.insert("News.finance_news_add", news);
            } else if (url.matches("(.*)news.sina(.*)")) {
                sqlSession.insert("News.news_add", news);
            } else if (url.matches("(.*)ent.sina(.*)")) {
                sqlSession.insert("News.ent_news_add", news);
            } else if (url.matches("(.*)mil.sina(.*)")) {
                sqlSession.insert("News.mil_news_add", news);
            } else if (url.matches("(.*)tech.sina(.*)")) {
                sqlSession.insert("News.tech_news_add", news);
            }
            sqlSession.commit();
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
     * 添加到已经处理的表.
     *
     * @param link 添加的链接
     */
    public void add_link_solved(String link) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            sqlSession.insert("News.links_solved_add", link);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    /**
     * 从数据库拿出要处理的链接，然后删除.
     *
     * @return 返回拿到的链接
     */
    public synchronized String processLink() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            String link = sqlSession.selectOne("News.get_link");
            sqlSession.delete("News.delete_link");
            sqlSession.commit();
            return link;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            throw e;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }

    /**
     * 查询已处理的表是否有该链接.
     *
     * @param link 要查询的链接
     * @return 含有该链接就返回true
     */
    public synchronized boolean findByLink(String link) {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        try {
            List<String> result = sqlSession.selectList("News.findBy_Link", link);
            return result.size() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            MybatisUtil.closeSqlSession();
        }
    }


}
