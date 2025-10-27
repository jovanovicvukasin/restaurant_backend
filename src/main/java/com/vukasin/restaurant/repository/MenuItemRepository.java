package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository <MenuItem, Long> {


}
