package com.fellowtraveler.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by igorkasyanenko on 12.12.16.
 */
public class User_ {
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> ssoId;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> gender;
    public static volatile SingularAttribute<User, String> imageUrl;
    public static volatile SetAttribute<User, UserProfile> userProfiles;
    public static volatile SetAttribute<User, Car> cars;

}
