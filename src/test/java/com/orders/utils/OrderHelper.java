package com.orders.utils;

import com.orders.domain.dto.ItemDto;
import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.domain.dto.UserDto;
import com.orders.domain.entity.ItemEntity;
import com.orders.domain.entity.OrderEntity;
import com.orders.domain.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

import static com.orders.utils.Constants.*;

public final class OrderHelper {

    static Integer quantity = 1;
    static Integer quantity2 = 2;

    public static OrderRequestDto generateOrderRequestDto() {
        return OrderRequestDto.builder()
                .quantity(quantity)
                .user(generateUserDto())
                .item(generateItemDto())
                .build();
    }

    public static OrderResponseDto generateOrderResponseDto() {
        return OrderResponseDto.builder()
                .user(generateUserDto())
                .item(generateItemDto())
                .quantity(quantity)
                .creationDate(Timestamp.from(Instant.now()))
                .build();
    }

    public static OrderResponseDto generateOrderResponseDto2() {
        return OrderResponseDto.builder()
                .user(generateUserDto2())
                .item(generateItemDto2())
                .quantity(quantity2)
                .creationDate(Timestamp.from(Instant.now()))
                .build();
    }

    public static OrderEntity generateOrderEntity() {
        return OrderEntity.builder()
                .id(1L)
                .quantity(quantity)
                .user(generateUserEntity())
                .item(generateItemEntity())
                .build();
    }

    public static OrderEntity generateOrderEntity2() {
        return OrderEntity.builder()
                .id(1L)
                .quantity(quantity)
                .user(generateUserEntity2())
                .item(generateItemEntity2())
                .build();
    }

    private static UserDto generateUserDto() {
        return UserDto.builder()
                .name(USER_NAME)
                .email(EMAIL)
                .build();
    }

    private static ItemDto generateItemDto() {
        return ItemDto.builder()
                .name(Constants.ITEM_NAME)
                .build();
    }

    private static UserDto generateUserDto2() {
        return UserDto.builder()
                .name(USER_NAME_2)
                .email(EMAIL_2)
                .build();
    }

    private static ItemDto generateItemDto2() {
        return ItemDto.builder()
                .name(ITEM_NAME_2)
                .build();
    }

    private static UserEntity generateUserEntity() {
        return UserEntity.builder()
                .name(USER_NAME)
                .email(EMAIL)
                .build();
    }

    private static ItemEntity generateItemEntity() {
        return ItemEntity.builder()
                .name(Constants.ITEM_NAME)
                .build();
    }

    private static UserEntity generateUserEntity2() {
        return UserEntity.builder()
                .name(USER_NAME_2)
                .email(EMAIL_2)
                .build();
    }

    private static ItemEntity generateItemEntity2() {
        return ItemEntity.builder()
                .name(ITEM_NAME_2)
                .build();
    }

}
