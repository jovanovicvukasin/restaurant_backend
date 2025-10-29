package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository <Order, Long> {

    @Query("SELECT SUM(o.totalAmount) FROM Order o " +
            "WHERE o.orderStatus = 'ACCEPTED' " +
            "AND o.orderDateTime >= :fromDate " +
            "AND o.orderDateTime < :toDate")
    Double calculateTotalRevenue(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    @Query("SELECT COUNT(o) FROM Order o " +
            "WHERE o.orderStatus = 'ACCEPTED' " +
            "AND o.orderDateTime >= :fromDate " +
            "AND o.orderDateTime < :toDate")
    Long countOrders(@Param("fromDate") LocalDateTime fromDate,
                     @Param("toDate") LocalDateTime toDate);

    @Query("SELECT oi.menuItem.name AS itemName, SUM(oi.quantity) AS totalSold " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE o.orderStatus = 'ACCEPTED' AND o.orderDateTime >= :fromDate AND o.orderDateTime < :toDate " +
            "GROUP BY oi.menuItem.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopSellingItems(@Param("fromDate") LocalDateTime fromDate,
                                       @Param("toDate") LocalDateTime toDate);





}
