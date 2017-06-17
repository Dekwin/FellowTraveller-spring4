package com.fellowtraveler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by igorkasyanenko on 20.05.17.
 */
@Entity
@Table(name = "REVIEW")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "TITLE", nullable = false)
    private String title;

    @NotNull
    @Column(name = "DATETIME", nullable = false)
    private Long datetime;

    //@NotEmpty
    @Column(name = "FOR_DRIVER", nullable = false)
    private Boolean forDriver;

    //@NotEmpty
    @Column(name = "TEXT", nullable = false)
    private String text;

    //@NotEmpty
    @Column(name = "RATING", nullable = false)
    private Integer rating;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SENDER_ID")
    //@JsonBackReference
    @JsonIgnoreProperties({"password", "email","ssoId","outgoingReviews","incomingReviews","userProfiles","cars","gender" })
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECIPIENT_ID")
    //@JsonBackReference
    @JsonIgnoreProperties({"password", "email","ssoId","outgoingReviews","incomingReviews","userProfiles","cars","gender" })
    private User recipient;

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

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public Boolean getForDriver() {
        return forDriver;
    }

    public void setForDriver(Boolean forDriver) {
        this.forDriver = forDriver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Review() {
    }
}
