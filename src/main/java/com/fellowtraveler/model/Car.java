package com.fellowtraveler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by igorkasyanenko on 19.03.17.
 */
@Entity
@Table(name="CAR")
public class Car implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name="TITLE", nullable=false)
    private String title;

    //@NotEmpty
    @Column(name="CAPACITY", nullable=false)
    private Integer capacity;

    //@NotEmpty
    @Column(name="YEAR", nullable=false)
    private Integer year;

   // @NotEmpty
    @Column(name="IMAGE", nullable=true)
    private String imageUrl;

    //@NotEmpty
    @Column(name="CONDITION", nullable=false)
    private Integer condition;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID")
    @JsonBackReference
    private User owner;

    public Car(String title, Integer capacity, Integer year, String imageUrl, Integer condition, User owner) {
        this.title = title;
        this.capacity = capacity;
        this.year = year;
        this.imageUrl = imageUrl;
        this.condition = condition;
        this.owner = owner;
    }


    public Car() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
