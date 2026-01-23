package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.Order;
import com.vukasin.restaurant.model.OrderType;
import com.vukasin.restaurant.model.RequestStatus;
import com.vukasin.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    @Query("""
    SELECT o FROM Order o
    JOIN o.user u
    WHERE (:status IS NULL OR o.orderStatus = :status)
      AND (:orderType IS NULL OR o.orderType = :orderType)
      AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
      AND (:fromDate IS NULL OR o.orderDateTime >= :fromDate)
      AND (:toDate IS NULL OR o.orderDateTime <= :toDate)
      AND (:minTotal IS NULL OR o.totalAmount >= :minTotal)
      AND (:maxTotal IS NULL OR o.totalAmount <= :maxTotal)
""")
    List<Order> searchOrders(@Param("status") RequestStatus status, @Param("orderType") OrderType orderType,@Param("email") String email,
                              @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, @Param("minTotal") Double minTotal, @Param("maxTotal") Double maxTotal);

    List<Order> findAllByUserAndOrderStatus(User user, String status);

}
