package com.fellowtraveler.dao;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.Review;

import java.util.List;

/**
 * Created by igorkasyanenko on 20.05.17.
 */
public interface ReviewDao {

    List<Review> findAllReviewsForUser(int userId);
    List<Review> findAllReviewsForDriver(int userId);
    List<Review> findAllReviewsForPassenger(int userId);

    void save(Review review);
    void deleteById(int id);

}
