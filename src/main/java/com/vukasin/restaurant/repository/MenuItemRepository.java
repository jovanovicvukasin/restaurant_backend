package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.ItemCategory;
import com.vukasin.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository <MenuItem, Long> {

    List<MenuItem> findByItemCategory (ItemCategory itemCategory);

    List<MenuItem> findByActive(Boolean active);

    List<MenuItem> findByItemCategoryAndActive(ItemCategory itemCategory, Boolean active);
}
