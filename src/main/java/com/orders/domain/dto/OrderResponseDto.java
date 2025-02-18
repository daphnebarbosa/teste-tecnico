package com.orders.domain.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private UserDto user;
    private Timestamp creationDate;
    private ItemDto item;
    private Integer quantity;
}
