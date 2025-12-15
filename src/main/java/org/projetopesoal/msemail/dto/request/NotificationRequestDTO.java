package org.projetopesoal.msemail.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.projetopesoal.msemail.enums.NotificationChannel;

@Data
public class NotificationRequestDTO {

    @Email
    @NotBlank
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;

    private NotificationChannel channel;
}