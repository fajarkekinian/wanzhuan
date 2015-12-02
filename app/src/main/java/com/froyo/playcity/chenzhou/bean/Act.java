package com.froyo.playcity.chenzhou.bean;

/**
 * Created by Administrator on 2015/11/28.
 */
public class Act {
  public String id;
    public String title;
    public String address;
    public String summary;
    public String intro;
    public String pics;
    public int category;
    public Img img;

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

    public void setPics(String pics) {
        this.pics = pics;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setImg(Img img) {
        this.img = img;
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

    public String getPics() {
        return pics;
    }

    public int getCategory() {
        return category;
    }

    public Img getImg() {
        return img;
    }

    public class Img{
        public String url;
        public String name;
        public String id;

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
