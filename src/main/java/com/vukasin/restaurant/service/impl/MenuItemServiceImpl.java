package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.model.ItemCategory;
import com.vukasin.restaurant.model.MenuItem;
import com.vukasin.restaurant.repository.MenuItemRepository;
import com.vukasin.restaurant.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public MenuItem create(MenuItem menuItem) {
        if (menuItem.getName() == null || menuItem.getName().isBlank()) {
            throw new RuntimeException("Name must not be empty");
        }

        if (menuItem.getItemCategory() == null) {
            throw new RuntimeException("Category must not be null");
        }

        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }


    @Override
    public List<MenuItem> findByActive(Boolean active) {
        return menuItemRepository.findByActive(active);
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id " + id));
    }

    @Override
    public MenuItem update(Long id, MenuItem menuItem) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id " + id));

        if (menuItem.getName() != null)
        {
            if (menuItem.getName().isBlank()) {
                throw new RuntimeException("Name is blank");
            }

            existing.setName(menuItem.getName());
        }

        if (menuItem.getItemCategory() != null) {
            existing.setItemCategory(menuItem.getItemCategory());
        }

        existing.setDescription(menuItem.getDescription());
        existing.setImageUrl(menuItem.getImageUrl());
        //existing.setPrices(menuItem.getPrices());


        return menuItemRepository.save(existing);
    }

    @Override
    public void toggleActive(Long id) {

        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id " + id));

        existing.setActive(!existing.isActive());
        menuItemRepository.save(existing);
    }

    @Override
    public List<MenuItem> findByItemCategoryActive(ItemCategory category, Boolean active) {
        return menuItemRepository.findByItemCategoryAndActive(category, active);
    }


}
