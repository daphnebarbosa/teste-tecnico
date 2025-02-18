package com.orders.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "user name cannot be null")
    @NotEmpty(message = "user name cannot be empty")
    private String name;

    @NotNull(message = "user name cannot be null")
    @NotEmpty(message = "user name cannot be empty")
    private String email;
}
