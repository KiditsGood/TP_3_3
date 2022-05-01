package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.ProductOrder;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductOrderId;
import com.vsu.sem6.tp.tp33.service.model.ProductOrderDto;

import com.vsu.sem6.tp.tp33.service.model.ProductOrderIdDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;


@Mapper
public abstract class ProductOrderMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "getOrderProductId")
    public abstract ProductOrderDto fromEntity(ProductOrder productOrder);

    @Mapping(target = "id", source = "id", qualifiedByName = "setOrderProductId")
    @Mapping(target = "order.id", source = "id", qualifiedByName = "setOrderId")
    @Mapping(target = "product.id", source = "id", qualifiedByName = "setProductId")

    public abstract ProductOrder toEntity(ProductOrderDto dto);
    @Named("setProductId")
    protected UUID setProductId(ProductOrderIdDto productOrderIdDto) {
        if (productOrderIdDto==null) return null;
        return productOrderIdDto.getProductId();
    }
    @Named("setOrderId")
    protected UUID setOrderId(ProductOrderIdDto productOrderIdDto) {
        if (productOrderIdDto==null) return null;
        return productOrderIdDto.getOrderId();
    }
    @Named("setOrderProductId")
    protected ProductOrderId setOrderProductId(ProductOrderIdDto productOrderIdDto) {
        if (productOrderIdDto==null) return null;
        return new ProductOrderId(productOrderIdDto.getOrderId(),productOrderIdDto.getProductId());
    }
    @Named("getOrderProductId")
    protected ProductOrderIdDto getOrderProductId(ProductOrderId productOrderId) {
        if (productOrderId==null) return null;
        return new ProductOrderIdDto(productOrderId.getOrderId(),productOrderId.getProductId());
    }
}
