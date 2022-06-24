package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.Order;
import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses={ProductOrderMapper.class})
public interface OrderMapper {
    //@Mapping(target = "user",ignore = true)
    OrderDto fromEntity(Order productOrder,@Context CycleAvoidingMappingContext context);

    Order toEntity(OrderDto dto);
}
