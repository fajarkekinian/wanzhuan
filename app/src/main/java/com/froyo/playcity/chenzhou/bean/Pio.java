package com.froyo.playcity.chenzhou.bean;

/**
 * Created by Administrator on 2015/12/15.
 */
public class Pio {

    /**
     * id : 566b93b60156490970a88af3
     * name : 佳乐超市(湖南省郴州市交通规费征稽处西南)
     * type : 购物服务;便民商店/便利店;便民商店/便利店
     * address : 107国道附近
     * location : {"lng":"113.010256","lat":"25.770089"}
     * udid : B02DF0042I
     * city : chenzhou
     * dataType : shop
     * tel :
     * createdAt : 2015-12-12T03:25:42.877Z
     * updatedAt : 2015-12-12T03:25:42.877Z
     */

    private String id;
    private String name;
    private String type;
    private String address;
    /**
     * lng : 113.010256
     * lat : 25.770089
     */

    private LocationEntity location;
    private String udid;
    private String city;
    private String dataType;
    private String tel;
    private String createdAt;
    private String updatedAt;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public String getUdid() {
        return udid;
    }

    public String getCity() {
        return city;
    }

    public String getDataType() {
        return dataType;
    }

    public String getTel() {
        return tel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static class LocationEntity {
        private String lng;
        private String lat;

        public void setLng(String lng) {
            this.lng = lng;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }
    }
}
