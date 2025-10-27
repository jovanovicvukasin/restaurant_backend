package com.vukasin.restaurant.service.impl;

import com.vukasin.restaurant.converter.ReservationConverter;
import com.vukasin.restaurant.dto.ReservationRequestDTO;
import com.vukasin.restaurant.dto.ReservationResponseDTO;
import com.vukasin.restaurant.model.RequestStatus;
import com.vukasin.restaurant.model.Reservation;
import com.vukasin.restaurant.model.RestaurantTable;
import com.vukasin.restaurant.model.User;
import com.vukasin.restaurant.repository.ReservationRepository;
import com.vukasin.restaurant.repository.RestaurantTableRepository;
import com.vukasin.restaurant.repository.UserRepository;
import com.vukasin.restaurant.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationConverter converter;
    @Autowired
    private ReservationConverter reservationConverter;


    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO request) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null");
        }
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findUserByEmail(email)
                             .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Reservation reservation = Reservation.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .numberOfGuests(request.getNumberOfGuests())
                .requestStatus(RequestStatus.NEW)
                .user(user).build();

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationConverter.toDTO(savedReservation);

    }

    @Override
    public ReservationResponseDTO assignTable(Long reservationId, Long tableId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("Table not found."));

        if(table.getSeats() < reservation.getNumberOfGuests()) {
            throw new IllegalArgumentException("Number of seats must be greater than the number of guests.");
        }

        boolean overlapping = reservationRepository.existsByTableAndTimeRange(tableId, reservation.getStartTime(), reservation.getEndTime());
        if (overlapping) {
            throw new IllegalArgumentException("This table is already reserved at that time.");
        }

        reservation.setTable(table);
        reservation.setRequestStatus(RequestStatus.ACCEPTED);
        reservationRepository.save(reservation);

        return reservationConverter.toDTO(reservation);
    }

    @Override
    public ReservationResponseDTO cancelReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        reservation.setRequestStatus(RequestStatus.CANCELED);
        reservationRepository.save(reservation);

        return reservationConverter.toDTO(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
       List<Reservation> reservations = reservationRepository.findAll();
       List<ReservationResponseDTO> dtos = new ArrayList<>();
       for (Reservation r : reservations) {
           dtos.add(reservationConverter.toDTO(r));
       }
       return dtos;
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        return reservationConverter.toDTO(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getUserReservations() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String email = authentication.getName();

       User user = userRepository.findUserByEmail(email)
               .orElseThrow(() -> new IllegalArgumentException("User not found."));

       List<Reservation> reservations = reservationRepository.findByUserId(user.getId());
       List<ReservationResponseDTO> dtos = new ArrayList<>();
       for (Reservation r : reservations) {
           dtos.add(reservationConverter.toDTO(r));
       }

       return dtos;

    }

}
