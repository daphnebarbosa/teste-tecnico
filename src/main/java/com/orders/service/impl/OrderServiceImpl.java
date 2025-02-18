package com.orders.service.impl;

import com.orders.domain.mapper.OrderMapper;
import com.orders.domain.repository.OrderRepository;
import com.orders.service.OrderService;
import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.domain.entity.OrderEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto order) {
        OrderEntity orderEntity = orderMapper.requestDtoToEntity(order);
        orderEntity.setCreationDate(Timestamp.from(Instant.now()));
        OrderEntity savedEntity = orderRepository.save(orderEntity);
        return orderMapper.entityToResponseDto(savedEntity);
    }

    @Override
    public List<OrderResponseDto> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponseDto> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::entityToResponseDto);
    }

    @Override
    public void deleteOrder(Long id) {
        Optional.of(orderRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(
                        order -> orderRepository.deleteById(id)
                );
    }
}
