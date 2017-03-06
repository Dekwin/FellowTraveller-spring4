package com.fellowtraveler.dao;

/**
 * Created by igorkasyanenko on 12.12.16.
 */

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractDao<PK extends Serializable, T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    protected CriteriaBuilder getCriteriaBuilder(){
        return entityManager.getCriteriaBuilder();
    }


    protected EntityManager getEntityManager(){
        return entityManager;
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) entityManager.find(persistentClass, key);
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    protected CriteriaQuery createEntityCriteria(){
      return   getCriteriaBuilder().createQuery(persistentClass);
    }

}