package com.vsu.sem6.tp.tp33.service.logic;

import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    PageDto<OrderDto> findAll(Integer pageNumber, Integer pageSize, String search);

    OrderDto create(OrderDto orderDto);

    OrderDto update(OrderDto orderDto);

    void deleteById(UUID id);

    OrderDto findById(UUID id);
    List<OrderDto> findOrdersByToken();
}
