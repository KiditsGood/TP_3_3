package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.Order;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductOrder;
import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import com.vsu.sem6.tp.tp33.service.model.ProductCartIdDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(uses={ProductOrderMapper.class})
public  interface OrderMapper {
      OrderDto fromEntity(Order productOrder,@Context CycleAvoidingMappingContext context);

      Order toEntity(OrderDto dto);

}
