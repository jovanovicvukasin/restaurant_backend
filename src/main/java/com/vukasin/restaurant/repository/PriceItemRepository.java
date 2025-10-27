package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.PriceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceItemRepository extends JpaRepository<PriceItem, Long> {

    @Query("SELECT p FROM PriceItem p " +
    "WHERE p.menuItem.id = :menuItemId " +
    "AND p.validFrom <= :newValidTo " +
    "AND p.validTo >= :newValidFrom")
    List<PriceItem> findOverlappingPrices(
            @Param("menuItemId") Long menuItemId,
            @Param("newValidFrom")LocalDate newValidFrom,
            @Param("newValidTo") LocalDate newValidTo);
}
