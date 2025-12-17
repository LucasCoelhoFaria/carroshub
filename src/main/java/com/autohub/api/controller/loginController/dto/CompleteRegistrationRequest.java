package com.autohub.api.controller.loginController.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompleteRegistrationRequest {
    private String token;
    private String businessName;
    private String whatsapp;
}
