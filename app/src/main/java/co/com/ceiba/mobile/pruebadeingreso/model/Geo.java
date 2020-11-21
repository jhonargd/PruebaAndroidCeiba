package co.com.ceiba.mobile.pruebadeingreso.model;

import java.io.Serializable;

public class Geo implements Serializable {
    float lat;
    float lng;
    public float getLat() {
        return lat;
    }
    public void setLat(float lat) {
        this.lat = lat;
    }
    public float getLng() {
        return lng;
    }
    public void setLng(float lng) {
        this.lng = lng;
    }
}
