package com.fellowtraveler.service;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.User;

import java.util.List;

/**
 * Created by igorkasyanenko on 19.03.17.
 */
public interface CarService {

    Car findById(int id);

    void saveCar(Car car);

    void updateCar(Car car);

    void deleteCarById(int id);
}
