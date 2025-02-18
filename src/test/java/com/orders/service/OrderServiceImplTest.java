package com.orders.service;

import com.orders.domain.dto.OrderRequestDto;
import com.orders.domain.dto.OrderResponseDto;
import com.orders.domain.entity.OrderEntity;
import com.orders.domain.mapper.OrderMapper;
import com.orders.domain.repository.OrderRepository;
import com.orders.service.impl.OrderServiceImpl;
import com.orders.utils.OrderHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.orders.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private OrderResponseDto orderResponseDto2;
    private OrderEntity orderEntity;
    private OrderEntity orderEntity2;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRequestDto = OrderHelper.generateOrderRequestDto();
        orderResponseDto = OrderHelper.generateOrderResponseDto();
        orderResponseDto2 = OrderHelper.generateOrderResponseDto2();
        orderEntity = OrderHelper.generateOrderEntity();
        orderEntity = OrderHelper.generateOrderEntity2();
    }

    @Test
    void shouldSaveAndReturnOrderResponseDto() {
        when(orderMapper.requestDtoToEntity(orderRequestDto)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderMapper.entityToResponseDto(orderEntity)).thenReturn(orderResponseDto);

        orderService.createOrder(orderRequestDto);

        assertNotNull(orderResponseDto);
        assertEquals(ITEM_NAME, orderResponseDto.getItem().getName());
        assertEquals(USER_NAME, orderResponseDto.getUser().getName());
        assertEquals(EMAIL, orderResponseDto.getUser().getEmail());

        verify(orderMapper).requestDtoToEntity(orderRequestDto);
        verify(orderRepository).save(orderEntity);
        verify(orderMapper).entityToResponseDto(orderEntity);
    }
}
