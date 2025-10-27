package com.vukasin.restaurant.repository;

import com.vukasin.restaurant.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByTableId(Long tableId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END" +
            " FROM Reservation r" +
            " WHERE r.table.id = :tableId" +
            " AND r.requestStatus = 'ACCEPTED'" +
            " AND" +
            " (r.startTime < :endTime AND r.endTime > :startTime)")
    boolean existsByTableAndTimeRange(@Param("tableId") Long tableId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
