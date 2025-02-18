package com.orders.domain.mapper;

import com.orders.domain.dto.ItemDto;
import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.domain.dto.UserDto;
import com.orders.domain.entity.ItemEntity;
import com.orders.domain.entity.OrderEntity;
import com.orders.domain.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto entityToResponseDto(final OrderEntity entity) {

        return OrderResponseDto.builder()
                .creationDate(entity.getCreationDate())
                .quantity(entity.getQuantity())
                .user(UserDto.builder()
                        .name(entity.getUser().getName())
                        .email(entity.getUser().getEmail())
                        .build())
                .item(ItemDto.builder()
                        .name(entity.getItem().getName())
                        .build())
                .build();
    }

    public OrderEntity requestDtoToEntity(OrderRequestDto orderRequestDto) {

        return OrderEntity.builder()
                .quantity(orderRequestDto.getQuantity())
                .item(ItemEntity.builder()
                        .name(orderRequestDto.getItem().getName())
                        .build())
                .user(UserEntity.builder()
                        .name(orderRequestDto.getUser().getName())
                        .email(orderRequestDto.getUser().getEmail())
                        .build())
                .build();
    }

}
