package com.bach.task_flow.mappers;

import com.bach.task_flow.domains.EmailOTP;
import com.bach.task_flow.dtos.requests.email.EmailRequest;
import com.bach.task_flow.dtos.responses.email.EmailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailOTP toEmailOTP(EmailRequest email);
    EmailResponse toEmailResponse(EmailOTP email);

}
