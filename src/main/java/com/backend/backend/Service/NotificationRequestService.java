package com.backend.backend.Service;


import com.backend.backend.dto.NotificationRequestDTO;
import org.springframework.http.ResponseEntity;

public interface NotificationRequestService {
    ResponseEntity<String> sendNotificationRequest(NotificationRequestDTO notificationRequestDTO);
}
