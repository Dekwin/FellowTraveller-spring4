package com.fellowtraveler.dao;

import com.fellowtraveler.model.User;
import com.fellowtraveler.model.User_;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by igorkasyanenko on 12.12.16.
 */



@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public User findById(int id) {
        User user = getByKey(id);
        if (user != null) {
            System.out.println("init");
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    public User findBySSO(String sso) {
          logger.info("SSO : {}", sso);
        CriteriaQuery crit = createEntityCriteria();
        Root<User> userRoot =  crit.from(User.class);
        crit.where(getCriteriaBuilder().equal(userRoot.get(User_.ssoId), sso));
        TypedQuery<User> q = getEntityManager().createQuery(crit);
        User user = null;

        List<User> list = q.getResultList();
        if(list.size() > 0){
            user = list.get(0);
        }


//        Criteria crit = createEntityCriteria();
//        crit.add(Restrictions.eq("ssoId", sso));
//        User user = (User) crit.uniqueResult();


        if (user != null) {
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {


        CriteriaQuery crit = createEntityCriteria();
        Root<User> userRoot =  crit.from(User.class);
        crit.distinct(true).orderBy(getCriteriaBuilder().asc(userRoot.get(User_.firstName)));
        TypedQuery<User> q = getEntityManager().createQuery(crit);
        List<User> users = q.getResultList();

//        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
//        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
//        List<User> users = (List<User>) criteria.list();

        // No need to fetch userProfiles since we are not showing them on list page. Let them lazy load.
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserProfiles());
        }*/

        return users;
    }

    public void save(User user) {
        persist(user);
    }

    public void deleteBySSO(String sso) {

        CriteriaQuery crit = createEntityCriteria();
        Root<User> userRoot =  crit.from(User.class);
        crit.where(getCriteriaBuilder().equal(userRoot.get(User_.ssoId), sso));
        TypedQuery<User> q = getEntityManager().createQuery(crit);
        User user = q.getSingleResult();

//        Criteria crit = createEntityCriteria();
//        crit.add(Restrictions.eq("ssoId", sso));
//        User user = (User) crit.uniqueResult();

        delete(user);
    }

}