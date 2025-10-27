package com.vukasin.restaurant.dto;

import com.vukasin.restaurant.model.RequestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponseDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfGuests;
    private RequestStatus status;
    private Long tableId;
    private Long userId;
}
