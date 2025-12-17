package com.autohub.api.controller.DashboardController.dto;

import lombok.Data;

@Data
public class ServiceUpdateRequest {
    private String name;
    private String description;
    private Boolean enabled;
}
