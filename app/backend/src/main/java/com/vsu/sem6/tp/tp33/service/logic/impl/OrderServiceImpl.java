package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.repository.OrderRepository;
import com.vsu.sem6.tp.tp33.service.logic.OrderService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.OrderMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    @Override
    public PageDto<OrderDto> findAll(Integer pageNumber, Integer totalPages) {
        Page<OrderDto> orderDtos = orderRepository
                .findAll(Pageable.ofSize(totalPages).withPage(pageNumber))
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()));

        return ImmutablePageDto.<OrderDto>builder()
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .items(orderDtos.getContent())
                .build();
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        return Optional.ofNullable(orderDto)
                .filter(dto -> dto.getId() == null)
                .map(orderMapper::toEntity)
                .map(orderRepository::save)
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        return Optional.ofNullable(orderDto)
                .filter(dto -> orderRepository.existsById(dto.getId()))
                .map(orderMapper::toEntity)
                .map(orderRepository::save)
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public void deleteById(UUID id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public OrderDto findById(UUID id) {
        return orderRepository
                .findById(id)
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }
}
