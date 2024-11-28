package com.huce.quiz_app.Mapper;


import com.huce.quiz_app.Request.UserCreationRequest;
import com.huce.quiz_app.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);


}

