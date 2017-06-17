package com.fellowtraveler.service;

import com.fellowtraveler.dao.CarDao;
import com.fellowtraveler.dao.ReviewDao;
import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by igorkasyanenko on 17.06.17.
 */
@Service("reviewService")
@Transactional("hibernate-transaction")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao dao;

    @Override
    public Review findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void saveReview(Review review) {
        dao.save(review);
    }

    @Override
    public void deleteReviewById(int id) {
        dao.deleteById(id);
    }

    @Override
    public List<Review> findAllForSender(int userId, int offset, int limit) {
        return dao.findAllForSender(userId,offset,limit);
    }

    @Override
    public List<Review> findAllForRecipient(int userId, boolean isForDriver, int offset, int limit) {
        return dao.findAllForRecipient(userId,isForDriver,offset,limit);
    }
}
