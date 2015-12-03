package com.froyo.playcity.chenzhou.bean;

/**
 * Created by Administrator on 2015/11/28.
 */
public class Act {

    private String id;
    private String title;
    private String address;
    private String summary;
    private String intro;
    private String city;
    private String type;


    private ImgEntity img;
    private String createdAt;
    private String updatedAt;
    private String actDate;
    private String pics;
    private String category;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getSummary() {
        return summary;
    }

    public String getIntro() {
        return intro;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
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

    public String getActDate() {
        return actDate;
    }

    public String getPics() {
        return pics;
    }

    public String getCategory() {
        return category;
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
