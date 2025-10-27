package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.RestaurantTableDTO;

import java.util.List;

public interface RestaurantTableService {

    RestaurantTableDTO create(RestaurantTableDTO restaurantTableDTO);
    List<RestaurantTableDTO> findAll();
    RestaurantTableDTO findById(Long id);
    RestaurantTableDTO update(Long id, RestaurantTableDTO restaurantTableDTO);
}
