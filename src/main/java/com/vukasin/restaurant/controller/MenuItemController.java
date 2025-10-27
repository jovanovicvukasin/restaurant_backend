package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.converter.MenuItemConverter;
import com.vukasin.restaurant.dto.MenuItemDTO;
import com.vukasin.restaurant.model.MenuItem;
import com.vukasin.restaurant.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemConverter menuItemConverter;

    @PostMapping
    public ResponseEntity<MenuItemDTO> create(@RequestBody MenuItemDTO menuItemDTO) {
        //dto -> entity
        MenuItem menuItem = menuItemConverter.toEntity(menuItemDTO);

        //service save in db
        MenuItem created = menuItemService.create(menuItem);

        if(created != null) {
            //entity -> dto
            MenuItemDTO createdDTO = menuItemConverter.toDTO(created);
            return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAll() {

        List<MenuItem> menuItems = menuItemService.findAll();

        List<MenuItemDTO> dtoList = new ArrayList<>();
        for(MenuItem menuItem : menuItems) {
            dtoList.add(menuItemConverter.toDTO(menuItem));
        }

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
        MenuItem menuItem = menuItemService.findById(id);

        MenuItemDTO menuItemDTO = menuItemConverter.toDTO(menuItem);
        return ResponseEntity.ok(menuItemDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItemById(@PathVariable Long id, @RequestBody MenuItemDTO menuItemDTO) {

        MenuItem menuItem = menuItemConverter.toEntity(menuItemDTO);
        MenuItem updated = menuItemService.update(id, menuItem);
        if(updated != null) {
            MenuItemDTO updatedDTO = menuItemConverter.toDTO(updated);
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();

    }




}
