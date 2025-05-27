package com.backend.backend.Controller;

import com.backend.backend.Service.ServiceImpl.NotificationServiceImpl;
import com.backend.backend.dto.NotificationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationServiceImpl impl;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequestDTO dto) {
        return impl.sendNotificationRequest(dto);
    }
}
