package com.fellowtraveler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by igorkasyanenko on 20.05.17.
 */
public class Review_ {
    public static volatile SingularAttribute<Review, Integer> id;
    public static volatile SingularAttribute<Review, String> title;
    public static volatile SingularAttribute<Review, Long> datetime;
    public static volatile SingularAttribute<Review, Boolean> isForDriver;
    public static volatile SingularAttribute<Review, String> text;
    public static volatile SingularAttribute<Review, Integer> rating;
    public static volatile SingularAttribute<Review, User> sender;
    public static volatile SingularAttribute<Review, User> recipient;
}
