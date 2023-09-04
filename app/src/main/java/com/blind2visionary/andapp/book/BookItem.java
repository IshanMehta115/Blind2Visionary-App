package com.blind2visionary.andapp.book;

public class BookItem {
    private String title;
    private String url;
    private String author;

    public BookItem()   {}

    public BookItem(String author, String title, String url ) {
        this.title = title;
        this.url = url;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
