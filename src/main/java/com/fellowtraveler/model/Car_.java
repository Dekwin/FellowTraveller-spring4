package com.fellowtraveler.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by igorkasyanenko on 19.03.17.
 */
public class Car_ {
    public static volatile SingularAttribute<Car, Integer> id;
    public static volatile SingularAttribute<Car, String> title;
    public static volatile SingularAttribute<Car, String> password;
    public static volatile SingularAttribute<Car, Integer> capacity;
    public static volatile SingularAttribute<Car, Integer> year;
    public static volatile SingularAttribute<Car, String> imageUrl;
    public static volatile SingularAttribute<Car, Integer> condition;
    public static volatile SingularAttribute<Car, User> owner;
}
