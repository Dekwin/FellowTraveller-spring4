package com.fellowtraveler.service;

import com.fellowtraveler.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by igorkasyanenko on 17.06.17.
 */

public interface ReviewService {
    Review findById(int id);

    void saveReview(Review review);

    void deleteReviewById(int id);

    List<Review> findAllForSender(int userId, int offset, int limit);
    List<Review> findAllForRecipient(int userId, boolean isForDriver, int offset, int limit);
}
