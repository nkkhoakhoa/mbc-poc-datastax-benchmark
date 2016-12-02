package com.nkk.gremlin.api.domain.entities;

import java.util.Date;

/**
 * @author KhoaNguyenKieu
 */
public class Page extends BaseEntity {

    private String title;
    private String author;
    private String content;
    private Date publishDate;

    public Page() {
    }

    public Page(String title, String author, String content, Date publishDate) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getInsertCommand() {
        return String.format("graph.addVertex(label, 'page', 'title', '%s', 'author','%s'," + " 'content','%s', 'publishDate', '%s')",
                getTitle(),
                getAuthor(),
                getContent(),
                getPublishDate().toString());
    }

}
