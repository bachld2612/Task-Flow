package com.bach.task_flow.mappers;

import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.user.AdminCreationRequest;
import com.bach.task_flow.dtos.responses.user.AdminCreationResponse;
import com.bach.task_flow.dtos.responses.user.UserResponse;
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
