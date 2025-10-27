package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.converter.RestaurantTableConverter;
import com.vukasin.restaurant.dto.RestaurantTableDTO;
import com.vukasin.restaurant.model.RestaurantTable;
import com.vukasin.restaurant.repository.RestaurantTableRepository;
import com.vukasin.restaurant.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Autowired
    private RestaurantTableConverter restaurantTableConverter;


    @Override
    public RestaurantTableDTO create(RestaurantTableDTO restaurantTableDTO) {

        if (restaurantTableDTO.getTableNumber() <= 0) {
            throw new IllegalArgumentException("Table number must be greater than 0.");
        }

        if (restaurantTableRepository.existsByTableNumber(restaurantTableDTO.getTableNumber())) {
            throw new IllegalArgumentException("Table number already exists.");
        }

        if (restaurantTableDTO.getSeats() < 1) {
            throw new IllegalArgumentException("Minimum 1 seat.");
        }


        RestaurantTable entity = restaurantTableConverter.toEntity(restaurantTableDTO);
        RestaurantTable savedEntity = restaurantTableRepository.save(entity);
        return restaurantTableConverter.toDTO(savedEntity);
    }

    @Override
    public List<RestaurantTableDTO> findAll() {
       List<RestaurantTable> restaurantTables = restaurantTableRepository.findAll();
       List<RestaurantTableDTO> result = new ArrayList<>();
       for (RestaurantTable t : restaurantTables) {
           result.add(restaurantTableConverter.toDTO(t));
       }

        return result;
    }

    @Override
    public RestaurantTableDTO findById(Long id) {
        RestaurantTable entity = restaurantTableRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Table with id " +id+ " not found."));

        return restaurantTableConverter.toDTO(entity);

    }

    @Override
    public RestaurantTableDTO update(Long id, RestaurantTableDTO restaurantTableDTO) {

        RestaurantTable entity = restaurantTableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Table with id " +id+ " not found."));

        if (restaurantTableDTO.getTableNumber() <= 0) {
            throw new IllegalArgumentException("Table number must be greater than 0.");
        }
        if(entity.getTableNumber() != restaurantTableDTO.getTableNumber()) {
            if(restaurantTableRepository.existsByTableNumber(restaurantTableDTO.getTableNumber())){
                throw new IllegalArgumentException("Table number already exists.");
            }
        }
        if (restaurantTableDTO.getSeats() < 1) {
            throw new IllegalArgumentException("Minimum 1 seat.");
        }


        entity.setTableNumber(restaurantTableDTO.getTableNumber());
        entity.setSeats(restaurantTableDTO.getSeats());

        RestaurantTable updated = restaurantTableRepository.save(entity);
        return restaurantTableConverter.toDTO(updated);

    }
}
