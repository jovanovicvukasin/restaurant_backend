package com.vukasin.restaurant.service;

import com.vukasin.restaurant.dto.ReservationRequestDTO;
import com.vukasin.restaurant.dto.ReservationResponseDTO;

import java.util.List;

public interface ReservationService {


    ReservationResponseDTO createReservation(ReservationRequestDTO request);
    ReservationResponseDTO assignTable(Long reservationId, Long tableId);
    ReservationResponseDTO cancelReservation(Long reservationId);
    List<ReservationResponseDTO> getAllReservations();
    ReservationResponseDTO getReservationById(Long id);
    List<ReservationResponseDTO> getUserReservations();
}
