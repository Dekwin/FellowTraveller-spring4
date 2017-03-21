package com.fellowtraveler.dao;

import com.fellowtraveler.model.UserProfile;
import com.fellowtraveler.model.UserProfile_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by igorkasyanenko on 12.12.16.
 */




@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>implements UserProfileDao{

    public UserProfile findById(int id) {
        return getByKey(id);
    }

    public UserProfile findByType(String type) {


        CriteriaQuery crit = createEntityCriteria();
        Root<UserProfile> userProfileRoot =  crit.from(UserProfile.class);
        System.out.println("find criteria "+crit);
        crit.where(getCriteriaBuilder().equal(userProfileRoot.get(UserProfile_.type), type));
        TypedQuery<UserProfile> q = getEntityManager().createQuery(crit);
        UserProfile userProfile = q.getSingleResult();

        return userProfile;
    }

    @SuppressWarnings("unchecked")
    public List<UserProfile> findAll(){

        CriteriaQuery crit = createEntityCriteria();
        System.out.println("criteria all "+crit);
        Root<UserProfile> userRoot =  crit.from(UserProfile.class);


        crit.orderBy(getCriteriaBuilder().asc(userRoot.get(UserProfile_.type))).distinct(true);

        TypedQuery<UserProfile> q = getEntityManager().createQuery(crit);

        List<UserProfile> users = q.getResultList();

        return users;
//        Criteria crit = createEntityCriteria();
//        crit.addOrder(Order.asc("type"));
//        return (List<UserProfile>)crit.list();
    }

}