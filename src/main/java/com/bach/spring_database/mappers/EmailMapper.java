package com.bach.spring_database.mappers;

import com.bach.spring_database.domains.EmailOTP;
import com.bach.spring_database.dtos.requests.email.EmailRequest;
import com.bach.spring_database.dtos.responses.email.EmailResponse;
import jakarta.validation.constraints.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailOTP toEmailOTP(EmailRequest email);
    EmailResponse toEmailResponse(EmailOTP email);

}
