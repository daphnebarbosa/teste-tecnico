package com.orders.domain.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {

    @Valid
    @NotNull(message = "item cannot be null")
    private ItemDto item;

    @Valid
    @NotNull(message = "user cannot be null")
    private UserDto user;

    @NotNull(message = "quantity cannot be null")
    private Integer quantity;
}
