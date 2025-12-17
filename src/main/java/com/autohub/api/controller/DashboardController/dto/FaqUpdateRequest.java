package com.autohub.api.controller.DashboardController.dto;

import lombok.Data;

@Data
public class FaqUpdateRequest {
    private String question;
    private String answer;
    private Boolean enabled;
}
