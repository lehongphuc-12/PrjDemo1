/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.City;

/**
 *
 * @author ASUS
 */
public class CityDao extends GenericDAO<City>{
    public CityDao () {
        super();
    }
    public static void main(String[] args) {
        CityDao dao = new CityDao();
        System.out.println(dao.findById(1).getCityName());
    }
}
