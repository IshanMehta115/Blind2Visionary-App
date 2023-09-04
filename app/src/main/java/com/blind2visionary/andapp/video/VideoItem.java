package com.blind2visionary.andapp.video;

public class VideoItem {
    private String title,url,tag,pic;

    public VideoItem() {
    }

    public VideoItem(String title, String url, String tag, String pic) {
        this.title = title;
        this.url = url;
        this.tag = tag;
        this.pic = pic;
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
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
}
