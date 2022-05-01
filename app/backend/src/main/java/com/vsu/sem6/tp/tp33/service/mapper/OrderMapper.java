package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.Order;
import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses={ProductOrderMapper.class})
public interface OrderMapper {
    OrderDto fromEntity(Order productOrder,@Context CycleAvoidingMappingContext context);

    Order toEntity(OrderDto dto);
}
