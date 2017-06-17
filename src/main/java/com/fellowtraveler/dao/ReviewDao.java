package com.fellowtraveler.dao;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.Review;

import java.util.List;

/**
 * Created by igorkasyanenko on 20.05.17.
 */
public interface ReviewDao {

    Review findById(int id);
    List<Review> findAllForSender(int userId, int offset, int limit);
    List<Review> findAllForRecipient(int userId, boolean isForDriver, int offset, int limit);
    void save(Review review);
    void deleteById(int id);

}
