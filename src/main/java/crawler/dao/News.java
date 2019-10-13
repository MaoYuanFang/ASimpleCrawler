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
    private Timestamp createdDate;
    /**
     * 数据修改日期.
     */
    private Timestamp modifiedDate;

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
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
