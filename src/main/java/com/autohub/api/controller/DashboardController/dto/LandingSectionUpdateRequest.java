package com.autohub.api.controller.DashboardController.dto;

import lombok.Data;

import java.util.Map;

@Data
public class LandingSectionUpdateRequest {
    private Map<String, Object> content;
    private Boolean enabled;
}
