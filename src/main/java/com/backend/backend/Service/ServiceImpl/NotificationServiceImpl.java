package com.backend.backend.Service.ServiceImpl;

import com.backend.backend.Service.NotificationRequestService;
import com.backend.backend.dto.NotificationRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationRequestService {

    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";
    @Override
    public ResponseEntity<String> sendNotificationRequest(NotificationRequestDTO notificationRequestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> payload = Map.of(
            "to",notificationRequestDTO.getTo(),
                "title",notificationRequestDTO.getTitle(),
                "body",notificationRequestDTO.getBody()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForEntity(EXPO_API_URL, request, String.class);
    }
}
