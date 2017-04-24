package com.fellowtraveler.dao;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.Car_;
import com.fellowtraveler.model.User;
import com.fellowtraveler.model.User_;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by igorkasyanenko on 19.03.17.
 */
@Repository("carDao")
public class CarDaoImpl extends AbstractDao<Integer, Car>  implements CarDao{

    @Override
    public Car findById(int id) {
        Car car = getByKey(id);
        return car;
    }

    @Override
    public void save(Car car) {
        persist(car);
    }

    @Override
    public void deleteById(int id) {
        CriteriaQuery criteria = createEntityCriteria();
        Root<Car> carRoot =  criteria.from(Car.class);
        criteria.where(getCriteriaBuilder().equal(carRoot.get(Car_.id), id));
        TypedQuery<Car> q = getEntityManager().createQuery(criteria);
        Car car = q.getSingleResult();
        delete(car);
    }

    @Override
    public List<Car> findAllForUser(int id) {
        CriteriaQuery criteria = createEntityCriteria();
        Root<Car> carRoot =  criteria.from(Car.class);
        criteria.distinct(true).where(getCriteriaBuilder().equal(carRoot.get(Car_.owner),id)).orderBy(getCriteriaBuilder().asc(carRoot.get(Car_.title)));
        TypedQuery<Car> q = getEntityManager().createQuery(criteria);
        return  q.getResultList();
    }





}
