package com.vukasin.restaurant.dto;

import com.vukasin.restaurant.model.OrderType;
import com.vukasin.restaurant.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private LocalDateTime dateAndTime;
    private RequestStatus orderStatus;
    private OrderType orderType;
    private Double totalAmount;
    private Long userId;
    private List<OrderItemDTO> orderItems;
}
