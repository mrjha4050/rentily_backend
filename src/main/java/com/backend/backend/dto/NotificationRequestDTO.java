package com.backend.backend.dto;

import lombok.Data;


@Data
public class NotificationRequestDTO {
    private String to;
    private String title;
    private String body;
}
