package com.fellowtraveler.model;

/**
 * Created by igorkasyanenko on 18.12.16.
 */
public class Profile {
    public interface PublicView {}
    public interface FriendsView extends PublicView{}
    public interface FamilyView extends FriendsView {}
}