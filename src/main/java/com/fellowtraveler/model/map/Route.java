package com.fellowtraveler.model.map;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * Created by igorkasyanenko on 16.04.17.
 */
@QueryResult
public class Route {
    private Long id;
    private Long owner;
    private String title;
    private Float price;
    private Long car;
    private Integer seats;

    private List<RoutePoint> points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public List<RoutePoint> getPoints() {
        return points;
    }

    public void setPoints(List<RoutePoint> points) {
        this.points = points;
    }

    public Route(Long id, Long owner, String title, Float price, Long car, Integer seats, List<RoutePoint> points) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.price = price;
        this.car = car;
        this.seats = seats;
        this.points = points;
    }

    public Route() {
    }

    @Override
    public String toString() {
        return String.format("{id:%s, owner:%s, title:%s, price:%s, car:%s, seats:%s, points:%s}", id, owner,title,price,car,seats,points);
    }
}
