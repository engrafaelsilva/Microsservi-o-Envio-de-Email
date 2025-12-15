package org.projetopesoal.msemail.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.projetopesoal.msemail.dto.request.NotificationRequestDTO;
import org.projetopesoal.msemail.entity.NotificationEntity;
import org.projetopesoal.msemail.enums.NotificationStatus;
import org.projetopesoal.msemail.repository.INotificationRepository;
import org.projetopesoal.msemail.service.INotificationService;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailNotificationService implements INotificationService {

    private final JavaMailSender mailSender;  // Injeção correta do JavaMailSender
    private final INotificationRepository repository;

    @Override
    @Async // Indica que o envio será assíncrono
    public void send(NotificationRequestDTO dto) {
        NotificationEntity entity = NotificationEntity.builder()
                .recipient(dto.getRecipient())
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .channel(dto.getChannel())
                .createdAt(LocalDateTime.now())
                .build();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(dto.getRecipient());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getMessage(), true);  // Envio de HTML, se necessário

            mailSender.send(message);  // Envio do e-mail
            entity.setStatus(NotificationStatus.SENT);  // Marca como enviado

        } catch (Exception e) {
            entity.setStatus(NotificationStatus.ERROR);  // Em caso de erro, marca como erro
            // Log da exceção (importante para debug)
            e.printStackTrace();
        }

        repository.save(entity);  // Persistência no banco
    }
}