package crawler.dao;

import java.sql.Timestamp;

/**
 * 存储新闻的对象,属性与表对应.
 */
public class News {
    /**
     * 自增的主键.
     */
    private int id;
    /**
     * 标题.
     */
    private String title;
    /**
     * 内容.
     */
    private String content;
    /**
     * 新闻日期.
     */
    private Timestamp date;
    /**
     * 新闻链接.
     */
    private String url;
    /**
     * 数据创建日期.
     */
    private Timestamp created_date;
    /**
     * 数据修改日期.
     */
    private Timestamp modified_date;
    /**
     * 新闻类型归属.
     */
    private int news_type;

    /**
     * 创建空的对象，设置、标题、内容、日期、链接即可.
     */
    public News() {
    }

    /**
     * 约定的get/set方法.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        if (date != null) {
            return (Timestamp) date.clone();
        }
        return null;
    }

    public void setDate(Timestamp date) {
        if (date != null) {
            this.date = (Timestamp) date.clone();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreated_date() {
        if (created_date != null) {
            return (Timestamp) created_date.clone();
        }
        return null;
    }

    public void setCreated_date(Timestamp created_date) {
        if (created_date != null) {
            this.created_date = (Timestamp) created_date.clone();
        }
    }

    public Timestamp getModified_date() {
        if (modified_date != null) {
            return (Timestamp) modified_date.clone();
        }
        return null;
    }

    public void setModified_date(Timestamp modified_date) {
        if (modified_date != null) {
            this.modified_date = (Timestamp) modified_date.clone();
        }
    }

    public int getNews_type() {
        return news_type;
    }

    public void setNews_type(int news_type) {
        this.news_type = news_type;
    }
}
