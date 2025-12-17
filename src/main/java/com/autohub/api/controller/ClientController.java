package com.autohub.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClientController {

    @GetMapping("/cliente")
    public String cliente(HttpServletRequest request) {
        return "Cliente: " + request.getAttribute("subdomain");
    }


}
