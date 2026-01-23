package com.vukasin.restaurant.controller;

import com.vukasin.restaurant.dto.OrderDTO;
import com.vukasin.restaurant.model.OrderType;
import com.vukasin.restaurant.model.RequestStatus;
import com.vukasin.restaurant.service.OrderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return order == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(order);
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        List<OrderDTO> orders = orderService.getUserOrders();
        return orders != null ? ResponseEntity.ok(orders) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        try{
            OrderDTO created = orderService.createOrder(orderDTO);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        try{
            OrderDTO canceled = orderService.cancelOrder(id);
            return ResponseEntity.status(200).body(canceled);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable Long id) {
        try {
            OrderDTO accepted = orderService.acceptOrder(id);
            return ResponseEntity.status(200).body(accepted);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderDTO>> searchOrders(@RequestParam(required = false) RequestStatus status,
                                                       @RequestParam(required = false) OrderType orderType,
                                                       @RequestParam(required = false) String email,
                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fromDate,
                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate toDate,
                                                       @RequestParam(required = false) Double minTotal,
                                                       @RequestParam(required = false) Double maxTotal)
            {
        return ResponseEntity.ok(orderService.searchOrders(status, orderType, fromDate, toDate, email, minTotal, maxTotal));
    }








}
