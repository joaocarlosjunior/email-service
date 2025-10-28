package com.joaocarlos.email_service.mappers;

import com.joaocarlos.email_service.dto.EmailAwsDTO;
import com.joaocarlos.email_service.dto.EmailOciDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.joaocarlos.email_service.common.EmailConstants.EMAIL_DTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmailMapperTest {
    @Autowired
    private EmailMapper emailMapper;

    @Test
    public void toAwsDTO_MapForEmailAwsDTO() {
        EmailAwsDTO emailAwsDTO = emailMapper.toAwsDTO(EMAIL_DTO);

        assertNotNull(emailAwsDTO);
        assertEquals(EMAIL_DTO.sender(), emailAwsDTO.sender());
        assertEquals(EMAIL_DTO.recipient(), emailAwsDTO.recipient());
        assertEquals(EMAIL_DTO.subject(), emailAwsDTO.subject());
        assertEquals(EMAIL_DTO.content(), emailAwsDTO.content());
    }

    @Test
    public void toAwsOCI_MapForEmailOciDTO() {
        EmailOciDTO emailOciDTO = emailMapper.toOciDTO(EMAIL_DTO);

        assertNotNull(emailOciDTO);
        assertEquals(EMAIL_DTO.sender(), emailOciDTO.senderEmail());
        assertEquals(EMAIL_DTO.recipient(), emailOciDTO.recipientEmail());
        assertEquals(EMAIL_DTO.subject(), emailOciDTO.subject());
        assertEquals(EMAIL_DTO.content(), emailOciDTO.body());
    }

    @Test
    public void toAwsDTO_WhenEmailDTOIsNull_returnsNull() {
        EmailAwsDTO emailAwsDTO = emailMapper.toAwsDTO(null);

        assertNull(emailAwsDTO);
    }

    @Test
    public void toAwsOCI_WhenEmailDTOIsNull_returnsNull() {
        EmailOciDTO emailOciDTO = emailMapper.toOciDTO(null);

        assertNull(emailOciDTO);
    }
}
