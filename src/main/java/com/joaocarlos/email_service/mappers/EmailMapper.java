package com.joaocarlos.email_service.mappers;

import com.joaocarlos.email_service.dto.EmailAwsDTO;
import com.joaocarlos.email_service.dto.EmailDTO;
import com.joaocarlos.email_service.dto.EmailOciDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    EmailAwsDTO toAwsDTO(EmailDTO email);

    @Mapping(source = "recipient", target = "recipientEmail")
    @Mapping(source = "sender", target = "senderEmail")
    @Mapping(source = "content", target = "body")
    EmailOciDTO toOciDTO(EmailDTO email);
}
