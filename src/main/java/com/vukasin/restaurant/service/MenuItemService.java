package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.MenuItemDTO;
import com.vukasin.restaurant.model.MenuItem;

import java.util.List;

public interface MenuItemService {

    MenuItem create(MenuItem menuItem);
    List<MenuItem> findAll();
    MenuItem findById(Long id);
    MenuItem update(Long id, MenuItem menuItem);
    void delete(Long id);

}
