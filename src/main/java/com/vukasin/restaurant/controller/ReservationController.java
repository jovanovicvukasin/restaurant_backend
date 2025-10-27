package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.dto.ReservationRequestDTO;
import com.vukasin.restaurant.dto.ReservationResponseDTO;
import com.vukasin.restaurant.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {

        ReservationResponseDTO reservationResponseDTO = reservationService.createReservation(reservationRequestDTO);
        return reservationResponseDTO != null ? ResponseEntity.ok(reservationResponseDTO) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{reservationId}/assign/{tableId}")
    public ResponseEntity<ReservationResponseDTO> assignTable(@PathVariable Long reservationId, @PathVariable Long tableId) {
        ReservationResponseDTO reservationResponseDTO = reservationService.assignTable(reservationId, tableId);
        return reservationResponseDTO != null ? ResponseEntity.ok(reservationResponseDTO) : ResponseEntity.badRequest().build();

    }

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<ReservationResponseDTO> cancelReservation(@PathVariable Long reservationId) {
        ReservationResponseDTO cancelled = reservationService.cancelReservation(reservationId);
        return cancelled != null ? ResponseEntity.ok(cancelled) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponseDTO>> getMyReservations() {
        List<ReservationResponseDTO> myReservations = reservationService.getUserReservations();
        return myReservations != null ? ResponseEntity.ok(myReservations) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservation(@PathVariable Long id) {
        ReservationResponseDTO reservation = reservationService.getReservationById(id);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.badRequest().build();
    }





}
