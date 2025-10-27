package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.dto.RestaurantTableDTO;
import com.vukasin.restaurant.model.RestaurantTable;
import com.vukasin.restaurant.service.RestaurantTableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;


    @GetMapping
    public ResponseEntity<List<RestaurantTableDTO>> getTables() {
        return ResponseEntity.ok(restaurantTableService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableDTO> getTableById(@PathVariable Long id) {
        RestaurantTableDTO restaurantTableDTO = restaurantTableService.findById(id);
        return restaurantTableDTO != null ? ResponseEntity.ok(restaurantTableDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RestaurantTableDTO> createTable(@Valid @RequestBody RestaurantTableDTO restaurantTableDTO) {

        RestaurantTableDTO created = restaurantTableService.create(restaurantTableDTO);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableDTO> updateTable(@Valid @RequestBody RestaurantTableDTO restaurantTableDTO, @PathVariable Long id) {

        RestaurantTableDTO updated = restaurantTableService.update(id, restaurantTableDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }


}
