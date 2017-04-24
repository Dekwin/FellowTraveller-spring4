package com.fellowtraveler.dao;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.User;

import java.util.List;

/**
 * Created by igorkasyanenko on 19.03.17.
 */
public interface CarDao {
    Car findById(int id);

    void save(Car car);

    void deleteById(int id);

    List<Car> findAllForUser(int id);
}
