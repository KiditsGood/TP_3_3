package com.vsu.sem6.tp.tp33.service.mapper;
import com.vsu.sem6.tp.tp33.persistence.entity.User;
import com.vsu.sem6.tp.tp33.service.model.UserDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses={OrderMapper.class,ProductCartMapper.class,RecipeMapper.class,ProductMapper.class})
public interface UserMapper {
    UserDto fromEntity(User user, @Context CycleAvoidingMappingContext context);

    User toEntity(UserDto dto);
}
