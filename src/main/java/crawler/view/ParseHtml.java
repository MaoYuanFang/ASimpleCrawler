package crawler.view;


import crawler.dao.News;
import crawler.dao.NewsDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;

/**
 * 处理Html页面.
 */
public class ParseHtml {

    /**
     * 绑定数据层访问对象.
     */
    private NewsDao newsDao = new NewsDao();

    /**
     * 网页的过滤规则.
     *
     * @param url 传入待筛选的链接
     * @return 符合规则返回true
     */
    private boolean filter(String url) {
        return url.matches("http(.*)//cj.sina(.*)") || url.matches("http(.*)//news.sina(.*)")
                || url.matches("http(.*)//finance.sina(.*)") || url.matches("http(.*)//sports.sina(.*)")
                || url.matches("http(.*)//ent.sina(.*)") || url.matches("http(.*)//mil.sina(.*)")
                || url.matches("http(.*)//tech.sina(.*)") || url.matches("http(.*)//nba.sina(.*)")
                || url.matches("https://sina(.*)");
    }

    /**
     * 待处理链接添加到库.
     *
     * @param url 待添加链接
     */
    private void addToDatabase(String url) {
        if (filter(url) && !newsDao.findByLink(url)) {
            newsDao.add_link_to_be_solved(url);
        }
    }

    /**
     * 获取时间字符串.
     *
     * @param document 文档对象
     * @return 返回获取的时间字符串
     */
    private String getTime(Document document) {
        String time = document.select("[property=\"article:published_time\"]").attr("content");
        if (time.equals("")) {
            return document.select("[class=date]").text();
        }
        return time;
    }


    /**
     * 把字符串转换成Timestamp格式，并生成Timestamp对象.
     *
     * @param time 待处理时间字符串
     * @return 返回Timestamp对象
     */
    private Timestamp parseTime(String time) {
        int length1 = "2019年10月09日 10:26".length();
        int length2 = "2019.10.09 11:14:00".length();
        int length3 = "2019-10-12T11:07:34+08:00".length();
        if (time.length() == length1) {
            String year = time.substring(0, 4);
            String month = time.substring(5, 7);
            String day = time.substring(8, 10);
            String hours = time.substring(12, 17);
            return Timestamp.valueOf(year + "-" + month + "-" + day + " " + hours + ":00");
        } else if (time.length() == length2) {
            return Timestamp.valueOf(time);
        } else if (time.length() == length3) {
            String year_month_day = time.substring(0, 10);
            String hours = time.substring(11, 19);
            return Timestamp.valueOf(year_month_day + " " + hours);
        }
        return null;
    }

    /**
     * 根据url在document里筛选出title、content，然后给News对象赋4个值，并返回.
     *
     * @param document doc对象
     * @param url 链接属性
     * @param timestamp 日期属性
     * @return 返回新闻对象
     */
    private News getNews(Document document, String url, Timestamp timestamp) {
        News news = new News();
        String title = document.select("[property=\"og:title\"]").attr("content");
        news.setTitle(title);
        news.setDate(timestamp);
        news.setUrl(url);
        String content;
        if (url.matches("(.*)cj.sina(.*)")) {
            content = document.select("[class=article-content-left]").select("[id=artibody]").text();
        } else {
            content = document.select("[class=art_pic_card art_content]").text();
        }
        news.setContent(content);
        return news;
    }


    /**
     * 主方法
     * 从数据库读取链接、处理链接、获取新闻、保存新闻、筛选链接、保存链接，然后循环处理.
     */
    public void parseUrl() {
        String url = null;
        while (url == null) {
            url = newsDao.processLink();
        }
        String html = new UrlToHtml(url).parse();
        if (html != null) {
            newsDao.add_link_solved(url);
            Document document = Jsoup.parse(html);
            String time = getTime(document);
            boolean indexPage = true;
            if (!time.equals("")) {
                indexPage = false;
                Timestamp timestamp = parseTime(time);
                News news = getNews(document, url, timestamp);
                newsDao.addNews(news, url);
            }
            if (indexPage) {
                if (url.matches("(.*)finance.sina(.*)")) {
                    selectUrl(document, "article", "data-open-url", "");
                } else if (url.matches("(.*)sports.sina(.*)") || url.matches("(.*)ent.sina(.*)")
                        || url.matches("(.*)nba.sina(.*)")) {
                    selectUrl(document, "a", "data-golink", "");
                } else {
                    selectUrl(document, "a", "href", "data-href");
                }
            } else {
                selectUrl(document, "a", "href", "data-href");
            }
        }

        parseUrl();
    }

    /**
     * 从doc中根据关键词获取相关链接.
     * @param document doc对象
     * @param select 选择标签
     * @param attr1 选择获取属性1
     * @param attr2 选择获取属性2
     */
    private void selectUrl(Document document, String select, String attr1, String attr2) {
        Elements links = document.select(select);
        boolean empty = attr2.equals("");
        for (Element element : links) {
            String url1 = element.attr(attr1);
            addToDatabase(url1);
            if (!empty) {
                String url2 = element.attr(attr2);
                addToDatabase(url2);
            }
        }

    }

}
