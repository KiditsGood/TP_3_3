package com.vsu.sem6.tp.tp33.service.logic.impl;

import com.google.common.base.Joiner;
import com.vsu.sem6.tp.tp33.config.SecurityService;
import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.persistence.entity.Order;
import com.vsu.sem6.tp.tp33.persistence.repository.OrderRepository;
import com.vsu.sem6.tp.tp33.persistence.repository.UserRepository;
import com.vsu.sem6.tp.tp33.persistence.specification.SearchOperation;
import com.vsu.sem6.tp.tp33.persistence.specification.order.OrderSpecificationBuilder;
import com.vsu.sem6.tp.tp33.service.logic.OrderService;
import com.vsu.sem6.tp.tp33.service.mapper.CycleAvoidingMappingContext;
import com.vsu.sem6.tp.tp33.service.mapper.OrderMapper;
import com.vsu.sem6.tp.tp33.service.model.ImmutablePageDto;
import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Validated
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final OrderMapper orderMapper;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, SecurityService securityService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.securityService=securityService;
        this.userRepository=userRepository;
    }


    @Override
    public PageDto<OrderDto> findAll(Integer pageNumber, Integer pageSize, String search) {
        OrderSpecificationBuilder builder = new OrderSpecificationBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        //(\w+?)|([А-я]+)|(\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2})|(.{8}-.{4}-.{4}-.{4}-.{12})

        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?|[А-я]+|\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}|.{8}-.{4}-.{4}-.{4}-.{12})(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            System.out.println(matcher.group(5));

            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }

        Specification<Order> spec = builder.build();
        Page<OrderDto> orderDtos = orderRepository
                .findAll(
                        spec,
                        PageRequest.of(pageNumber,pageSize)
                )
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()));

        return ImmutablePageDto.<OrderDto>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(orderDtos.getTotalElements())
                .items(orderDtos.getContent())
                .build();
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        orderDto=Optional.ofNullable(orderDto)
                .filter(dto -> dto.getId() == null)
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
        Order order=orderMapper.toEntity(orderDto);
        order.setUser(userRepository.getById(securityService.getUserId()));
        return orderMapper.fromEntity(orderRepository.save(order),new CycleAvoidingMappingContext());


    }

    @Override
    public OrderDto update(OrderDto orderDto) {

        return Optional.ofNullable(orderDto)
                .filter(dto -> orderRepository.existsById(dto.getId()))
                .filter(o->securityService.checkAccess(o.getUser().getId()))
                .map(orderMapper::toEntity)
                .map(orderRepository::save)
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong data"));
    }

    @Override
    public void deleteById(UUID id) {
        if (orderRepository.existsById(id)) {
            if (!securityService.checkAccess(orderRepository.getById(id).getUser().getId())){
                throw new ApiRequestException("access denied");
            }
            orderRepository.deleteById(id);
        } else {
            throw new ApiRequestException("Wrong id");
        }
    }

    @Override
    public OrderDto findById(UUID id) {
        if (!securityService.checkAccess(orderRepository.getById(id).getUser().getId())){
            throw new ApiRequestException("access denied");
        }
        return orderRepository
                .findById(id)
                .map(entity->orderMapper.fromEntity(entity,new CycleAvoidingMappingContext()))
                .orElseThrow(() -> new ApiRequestException("Wrong id"));
    }

    @Override
    public List<OrderDto> findOrdersByToken() {
        List<Order> orders=orderRepository.findByUser(userRepository.findById(securityService.getUserId()).get());
        List<OrderDto> dtos= new ArrayList<>();
        orders.forEach(entity->dtos.add(orderMapper.fromEntity(entity,new CycleAvoidingMappingContext())));
        return dtos;
    }
}
