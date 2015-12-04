package com.froyo.playcity.chenzhou.bean;

/**
 * Created by Administrator on 2015/12/4.
 */
public class Banner {

    private String id;
    private String name;
    /**
     * url : http://a98a31053f8476f03aae.b0.upaiyun.com/apicloud/2930bee1ae195ed8d0f8f0d1f25a3cc7.jpg
     * name : 228H.jpg
     * id : 5660ff876e1e86e66968d207
     */

    private ImgEntity img;
    private String model;
    private String model_id;
    private String createdAt;
    private String updatedAt;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(ImgEntity img) {
        this.img = img;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
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

    public String getName() {
        return name;
    }

    public ImgEntity getImg() {
        return img;
    }

    public String getModel() {
        return model;
    }

    public String getModel_id() {
        return model_id;
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
