package com.bach.spring_database.mappers;


import com.bach.spring_database.domains.User;
import com.bach.spring_database.dtos.requests.auth.RegisterRequest;
import com.bach.spring_database.dtos.responses.auth.RegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User toUser(RegisterRequest registerRequest);

    RegisterResponse toResponse(User user);

}
