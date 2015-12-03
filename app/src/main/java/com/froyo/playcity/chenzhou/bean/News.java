package com.froyo.playcity.chenzhou.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/30.
 */
public class News implements Serializable {
    private String id;
    private String type;
    private String title;
    private String summary;
    private String content;
    private String from;
    private Object source;
    private String date;
    private String pics;


    private ImgEntity img;
    private String createdAt;
    private String updatedAt;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public void setImg(ImgEntity img) {
        this.img = img;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public Object getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    public String getPics() {
        return pics;
    }

    public ImgEntity getImg() {
        return img;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static class ImgEntity {
        private String url;
        private String name;
        private String id;

        public void setUrl(String url) {
            this.url = url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}
