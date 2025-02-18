package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.domain.dto.ItemDto;
import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.domain.dto.UserDto;
import com.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    private static final String ITEM_NAME = "Notebook";

    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;


    @BeforeEach
    void setUp() {
        Integer quantity = 2;

        UserDto userDto = UserDto.builder()
                .name("Test")
                .email("test@mail.com")
                .build();

        ItemDto itemDto = ItemDto.builder()
                .name(ITEM_NAME)
                .build();

        orderRequestDto = OrderRequestDto.builder()
                .quantity(quantity)
                .user(userDto)
                .item(itemDto)
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .user(userDto)
                .item(itemDto)
                .quantity(quantity)
                .creationDate(Timestamp.from(Instant.now()))
                .build();
    }

}