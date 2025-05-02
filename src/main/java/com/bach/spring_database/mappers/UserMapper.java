package com.bach.spring_database.mappers;

import com.bach.spring_database.domains.User;
import com.bach.spring_database.dtos.requests.user.AdminCreationRequest;
import com.bach.spring_database.dtos.responses.user.AdminCreationResponse;
import com.bach.spring_database.dtos.responses.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    User toUser(AdminCreationRequest request);
    AdminCreationResponse toAdminCreationResponse(User user);
    UserResponse toUserResponse(User user);

}
