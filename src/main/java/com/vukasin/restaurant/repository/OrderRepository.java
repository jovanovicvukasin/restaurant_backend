package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.Order;
import com.vukasin.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByUserAndOrderStatus(User user, String status);
}
