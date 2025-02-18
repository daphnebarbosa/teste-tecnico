package com.orders.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    @NotNull(message = "item name cannot be null")
    @NotEmpty(message = "item name cannot be empty")
    private String name;
}
