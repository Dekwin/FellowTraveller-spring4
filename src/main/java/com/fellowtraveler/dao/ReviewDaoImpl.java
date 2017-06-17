package com.fellowtraveler.dao;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.Car_;
import com.fellowtraveler.model.Review;
import com.fellowtraveler.model.Review_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by igorkasyanenko on 20.05.17.
 */

@Repository("reviewDao")
public class ReviewDaoImpl extends AbstractDao<Integer, Review>  implements ReviewDao {
    @Override
    public Review findById(int id) {
        Review review = getByKey(id);
        return review;
    }

    @Override
    public void save(Review review) {
        persist(review);
    }

    @Override
    public List<Review> findAllForSender(int userId, int offset, int limit) {
        CriteriaQuery criteria = createEntityCriteria();
        Root<Review> reviewRoot =  criteria.from(Review.class);
        criteria.distinct(true).where(
                getCriteriaBuilder().equal(reviewRoot.get(Review_.sender),userId)
        ).orderBy(getCriteriaBuilder().desc(reviewRoot.get(Review_.datetime)));
        TypedQuery<Review> q = getEntityManager().createQuery(criteria);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return  q.getResultList();
       // return null;
    }

    @Override
    public List<Review> findAllForRecipient(int userId, boolean isForDriver, int offset, int limit) {
        CriteriaQuery criteria = createEntityCriteria();
        Root<Review> reviewRoot =  criteria.from(Review.class);
        criteria.distinct(true).where(
                getCriteriaBuilder().and(
                        getCriteriaBuilder().equal(reviewRoot.get(Review_.recipient),userId),
                        getCriteriaBuilder().equal(reviewRoot.get(Review_.forDriver),isForDriver)
                )
        ).orderBy(getCriteriaBuilder().desc(reviewRoot.get(Review_.datetime)));
        TypedQuery<Review> q = getEntityManager().createQuery(criteria);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return  q.getResultList();
        // return null;
    }
//
//    @Override
//    public List<Review> findAllForDriverAsRecipient(int userId) {
//        CriteriaQuery criteria = createEntityCriteria();
//        Root<Review> reviewRoot =  criteria.from(Review.class);
//        criteria.distinct(true).where(getCriteriaBuilder().equal(reviewRoot.get(Review_.isForDriver),reviewRoot)  .equal(reviewRoot.get(Review_.recipient),reviewRoot)).orderBy(getCriteriaBuilder().asc(reviewRoot.get(Review_.title)));
//        TypedQuery<Review> q = getEntityManager().createQuery(criteria);
//        q.setFirstResult(offset);
//        q.setMaxResults(limit);
//        return  q.getResultList();
//    }
//
//    @Override
//    public List<Review> findAllForPassengerAsRecipient(int userId) {
//        return null;
//    }

    @Override
    public void deleteById(int id) {
        CriteriaQuery criteria = createEntityCriteria();
        Root<Review> reviewRoot =  criteria.from(Review.class);
        criteria.where(getCriteriaBuilder().equal(reviewRoot.get(Review_.id), id));
        TypedQuery<Review> q = getEntityManager().createQuery(criteria);
        Review review = q.getSingleResult();
        delete(review);
    }


}
