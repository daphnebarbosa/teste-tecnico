package com.orders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.service.OrderService;
import com.orders.utils.OrderHelper;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.orders.utils.Constants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private OrderResponseDto orderResponseDto2;

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
        orderRequestDto = OrderHelper.generateOrderRequestDto();
        orderResponseDto = OrderHelper.generateOrderResponseDto();
        orderResponseDto2 = OrderHelper.generateOrderResponseDto2();
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

        List<OrderResponseDto> orders = Arrays.asList(orderResponseDto, orderResponseDto2);

        when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].user.name").value(USER_NAME))
                .andExpect(jsonPath("$[1].quantity").value(2))
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
    void shouldReturnNoContentWhenOrderIsDeleted() throws Exception {

        doNothing().when(orderService).deleteOrder(ODER_ID);

        mockMvc.perform(delete(URL.concat("/") + ODER_ID))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Order deleted successfully"));

        verify(orderService, times(1)).deleteOrder(ODER_ID);
    }

}