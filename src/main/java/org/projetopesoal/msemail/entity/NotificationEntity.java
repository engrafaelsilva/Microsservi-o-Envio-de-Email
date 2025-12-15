package org.projetopesoal.msemail.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import org.projetopesoal.msemail.enums.NotificationChannel;
import org.projetopesoal.msemail.enums.NotificationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;
}