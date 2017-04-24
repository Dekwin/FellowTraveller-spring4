package com.fellowtraveler.service;

import com.fellowtraveler.dao.CarDao;
import com.fellowtraveler.dao.UserDao;
import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by igorkasyanenko on 19.03.17.
 */

@Service("carService")
@Transactional("hibernate-transaction")
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao dao;

    @Override
    public Car findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void saveCar(Car car) {
        dao.save(car);
    }

    @Override
    public void updateCar(Car car) {
        Car entity = dao.findById(car.getId());
        if (entity != null) {
            entity.setImageUrl(car.getImageUrl());
            entity.setCapacity(car.getCapacity());
            entity.setCondition(car.getCondition());
            entity.setTitle(car.getTitle());
            entity.setYear(car.getYear());
        }
    }


    @Override
    public void deleteCarById(int id) {
        dao.deleteById(id);
    }
}
