package com.autohub.api.controller.loginController.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class GoogleLoginRequest {
    private String token;
}
