package com.vukasin.restaurant.converter;

import com.vukasin.restaurant.dto.ReservationRequestDTO;
import com.vukasin.restaurant.dto.ReservationResponseDTO;
import com.vukasin.restaurant.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public ReservationResponseDTO toDTO(Reservation entity) {
        if (entity == null) return null;

        return ReservationResponseDTO.builder()
                .id(entity.getId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .numberOfGuests(entity.getNumberOfGuests())
                .status(entity.getRequestStatus())
                .tableId(entity.getTable() != null ? entity.getTable().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
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
