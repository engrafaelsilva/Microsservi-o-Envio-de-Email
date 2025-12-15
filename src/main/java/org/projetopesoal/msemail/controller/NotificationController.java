package org.projetopesoal.msemail.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.projetopesoal.msemail.dto.request.NotificationRequestDTO;
import org.projetopesoal.msemail.dto.response.NotificationResponseDTO;
import org.projetopesoal.msemail.service.INotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService service;

    @GetMapping("/")
    public String health() {
        return "Email Notification Service is running 🚀";
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> send(
            @RequestBody @Valid NotificationRequestDTO dto) {

        service.send(dto);
        return ResponseEntity.ok(
                new NotificationResponseDTO("Notificação enviada com sucesso")
        );
    }
}
