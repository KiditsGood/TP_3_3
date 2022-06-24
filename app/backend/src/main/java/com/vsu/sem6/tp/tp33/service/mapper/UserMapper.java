package com.vsu.sem6.tp.tp33.service.mapper;
import com.vsu.sem6.tp.tp33.persistence.entity.User;
import com.vsu.sem6.tp.tp33.service.model.RegistrationUserDto;
import com.vsu.sem6.tp.tp33.service.model.UserDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(uses={ProductCartMapper.class,RecipeMapper.class,ProductMapper.class})
public abstract class UserMapper {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    abstract public UserDto fromEntity(User user, @Context CycleAvoidingMappingContext context);

    abstract public User toEntity(UserDto dto);
   // abstract public RegistrationUserDto authFromEntity(User user);
//@Mapping(target = "password",source = "password",qualifiedByName = "setPassword")
    abstract public User regToEntity(RegistrationUserDto dto);
//@Named("setPassword")
//    String setPassword(String password){
//        if (password==null){
//            return null;
//        }
//        return passwordEncoder.encode(password);
//
//    }
}
