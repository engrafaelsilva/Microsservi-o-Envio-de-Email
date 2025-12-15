package org.projetopesoal.msemail.service;

import org.projetopesoal.msemail.dto.request.NotificationRequestDTO;

public interface INotificationService {
    void send(NotificationRequestDTO dto);
}
