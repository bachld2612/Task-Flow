package com.bach.task_flow.mappers;


import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.auth.RegisterRequest;
import com.bach.task_flow.dtos.responses.auth.InfoResponse;
import com.bach.task_flow.dtos.responses.auth.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface AuthMapper {

    User toUser(RegisterRequest registerRequest);

    RegisterResponse toResponse(User user);

    InfoResponse toInfoResponse(User user);

}
