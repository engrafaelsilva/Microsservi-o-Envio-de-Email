package org.projetopesoal.msemail.repository;

import org.projetopesoal.msemail.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
