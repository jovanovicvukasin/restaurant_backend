package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.*;
import com.vukasin.restaurant.model.Reservation;
import com.vukasin.restaurant.model.RestaurantTable;
import com.vukasin.restaurant.model.User;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public ReservationResponseDTO toDTO(Reservation entity) {
        if (entity == null) return null;

        User user = entity.getUser();
        UserInfoDTO userDTO = null;

        if (user != null) {
            userDTO = new UserInfoDTO(
                    user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getPhone()
            );
        }

        RestaurantTable table = entity.getTable();
        RestaurantTableDTO tableDTO = null;

        if (table != null) {
            tableDTO = new RestaurantTableDTO(
                    table.getId(),
                    table.getTableNumber(),
                    table.getSeats()
            );
        }

        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .numberOfGuests(entity.getNumberOfGuests())
                .status(entity.getRequestStatus())
                .table(tableDTO)
                .user(userDTO)
                .build();
    }

    public Reservation toEntity(ReservationRequestDTO dto) {
        if (dto == null) return null;

        Reservation reservation = new Reservation();
        reservation.setStartTime(dto.getStartTime());
        reservation.setEndTime(dto.getEndTime());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        return reservation;
    }
}
