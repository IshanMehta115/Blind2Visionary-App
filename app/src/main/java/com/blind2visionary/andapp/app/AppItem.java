package com.blind2visionary.andapp.app;

public class AppItem {
    private String icon, name, desc, url;

    public AppItem()    {}

    public AppItem(String icon, String name, String desc, String url)  {
        this.icon = icon;
        this.name = name;
        this.desc = desc;
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
