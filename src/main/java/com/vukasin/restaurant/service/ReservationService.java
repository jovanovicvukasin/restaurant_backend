package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.ReservationRequestDTO;
import com.vukasin.restaurant.dto.ReservationResponseDTO;
import com.vukasin.restaurant.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {


    ReservationResponseDTO createReservation(ReservationRequestDTO request);
    ReservationResponseDTO assignTable(Long reservationId, Long tableId);
    ReservationResponseDTO cancelReservation(Long reservationId);
    List<ReservationResponseDTO> getAllReservations();
    ReservationResponseDTO getReservationById(Long id);
    List<ReservationResponseDTO> getUserReservations();
    List<ReservationResponseDTO> searchReservations(RequestStatus status, LocalDateTime from, LocalDateTime to, String email, Integer tableNumber);
}
