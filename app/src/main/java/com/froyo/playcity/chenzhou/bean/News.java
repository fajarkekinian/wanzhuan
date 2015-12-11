package com.froyo.playcity.chenzhou.bean;

import java.io.Serializable;
import java.util.ArrayList;

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
    private ArrayList<PicEntity> pics;


    private String img;
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

    public void setPics(ArrayList<PicEntity> pics) {
        this.pics = pics;
    }

    public void setImg(String img) {
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

    public ArrayList<PicEntity> getPics() {
        return pics;
    }

    public String getImg() {
        return img;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static class PicEntity {
        private String url;


        public void setUrl(String url) {
            this.url = url;
        }



        public String getUrl() {
            return url;
        }


    }
}
