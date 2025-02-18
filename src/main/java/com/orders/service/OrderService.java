package com.orders.service;

import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto order);

    List<OrderResponseDto> findAll();

    Optional<OrderResponseDto> findById(Long id);

    void deleteOrder(Long id);
}
