package com.fellowtraveler.model.map;

/**
 * Created by igorkasyanenko on 16.04.17.
 */

public class SearchPoint {

    private Double latitude;
    private Double longitude;
    private Double radius;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public SearchPoint(Double latitude, Double longitude, Double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public SearchPoint() {
    }
}
