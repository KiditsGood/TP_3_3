package com.vsu.sem6.tp.tp33.service.mapper;

import com.vsu.sem6.tp.tp33.persistence.entity.ProductCart;
import com.vsu.sem6.tp.tp33.persistence.entity.ProductCartId;
import com.vsu.sem6.tp.tp33.service.model.ProductCartDto;
import com.vsu.sem6.tp.tp33.service.model.ProductCartIdDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper
public abstract class ProductCartMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "getProductCartId")
    public abstract ProductCartDto fromEntity(ProductCart productCart);

    @Mapping(target = "id", source = "id", qualifiedByName = "setProductCartId")
    @Mapping(target = "user.id", source = "id", qualifiedByName = "setUserId")
    @Mapping(target = "product.id", source = "id", qualifiedByName = "setProductId")

    public abstract ProductCart toEntity(ProductCartDto dto);
    @Named("setProductId")
    protected UUID setProductId(ProductCartIdDto productCartIdDto) {
        if (productCartIdDto==null) return null;
        return productCartIdDto.getProductId();
    }
    @Named("setUserId")
    protected UUID setOrderId(ProductCartIdDto productCartIdDto) {
        if (productCartIdDto==null) return null;
        return productCartIdDto.getUserId();
    }
    @Named("setProductCartId")
    protected ProductCartId setProductCartId(ProductCartIdDto productCartIdDto) {
        if (productCartIdDto==null) return null;
        return new ProductCartId(productCartIdDto.getUserId(),productCartIdDto.getProductId());
    }
    @Named("getProductCartId")
    protected ProductCartIdDto getProductCartId(ProductCartId productCartId) {
        if (productCartId==null) return null;
        return new ProductCartIdDto(productCartId.getUserId(),productCartId.getProductId());
    }
}
