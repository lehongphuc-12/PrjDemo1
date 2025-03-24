/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.CityDao;
import java.util.List;
import model.City;

/**
 *
 * @author ASUS
 */
public class CityService {

    private CityDao cityDao;

    public CityService() {
        cityDao = new CityDao();
    }

    public List<City> getAllCity() {
        return cityDao.findAll();
    }

    public static void main(String[] args) {
        CityService citySer = new CityService();
        for (City city : citySer.getAllCity()) {
            System.out.println(city.getCityName());
        }

    }
}
