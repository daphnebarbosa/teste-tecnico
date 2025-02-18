package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.domain.dto.ItemDto;
import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.domain.dto.UserDto;
import com.orders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    private static final String ITEM_NAME = "Notebook";
    private static final String ITEM_NAME_2 = "Playstation 5";
    private static final String USER_NAME = "Test";
    private static final String USER_NAME_2 = "Test 2";
    private static final String EMAIL = "test@email.com";
    private static final String EMAIL_2 = "test2@email.com";
    private static final String URL = "/api/orders";
    private static final Long ODER_ID = 1L;

    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private OrderResponseDto orderResponseDto1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderController orderController;

    @MockBean
    private OrderService orderService;


    @BeforeEach
    void setUp() {
        Integer quantity = 2;

        UserDto userDto = UserDto.builder()
                .name(USER_NAME)
                .email(EMAIL)
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

        orderResponseDto1 = OrderResponseDto.builder()
                .user(UserDto.builder()
                        .name(USER_NAME_2)
                        .email(EMAIL_2)
                        .build())
                .item(ItemDto.builder()
                        .name(ITEM_NAME_2)
                        .build())
                .quantity(1)
                .creationDate(Timestamp.from(Instant.now()))
                .build();
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(orderResponseDto);
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.item.name", is(orderResponseDto.getItem().getName())))
                .andExpect(jsonPath("$.user.name", is(orderResponseDto.getUser().getName())));
    }

    @Test
    void shouldRetrieveAllOrdersSuccessfullyWhenOrderExists() throws Exception {

        List<OrderResponseDto> orders = Arrays.asList(orderResponseDto, orderResponseDto1);

        when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[0].user.name").value(USER_NAME))
                .andExpect(jsonPath("$[1].quantity").value(1))
                .andExpect(jsonPath("$[1].user.name").value(USER_NAME_2));
    }

    @Test
    void shouldReturnOrderWhenOrderExists() throws Exception {

        when(orderService.findById(ODER_ID)).thenReturn(Optional.of(orderResponseDto));

        mockMvc.perform(get(URL.concat("/") + ODER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value(USER_NAME))
                .andExpect(jsonPath("$.item.name").value(ITEM_NAME));
    }

    @Test
    void deleteOrder_ShouldReturnNoContent_WhenOrderIsDeleted() throws Exception {

        doNothing().when(orderService).deleteOrder(ODER_ID);

        mockMvc.perform(delete(URL.concat("/") + ODER_ID))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Order deleted successfully"));

        verify(orderService, times(1)).deleteOrder(ODER_ID);
    }

}