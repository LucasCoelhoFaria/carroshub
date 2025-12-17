package com.autohub.api.controller;

import com.autohub.api.service.BusinessService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LandingController {

    private final BusinessService businessService;

    @GetMapping
    public String landing(HttpServletRequest request, Model model) {
        String host = request.getHeader("Host"); // exemplo: estetica.autohub.com.br
        String subdomain = extractSubdomain(host);

        if (subdomain == null || subdomain.isBlank()) {
            return "redirect:/login";
        }

        return businessService.getFullBusinessData(subdomain)
                .map(data -> {
                    populateModel(model, data);
                    return "landing"; // Thymeleaf template
                })
                .orElse("redirect:/login");
    }

    private void populateModel(Model model, Map<String, Object> data) {
        model.addAttribute("businessName", data.get("businessName"));
        model.addAttribute("whatsapp", data.get("whatsapp"));

        // Landing sections
        model.addAttribute("hero", data.get("hero"));
        model.addAttribute("footer", data.get("footer"));
        model.addAttribute("services", data.get("services"));
        model.addAttribute("faq", data.get("faq"));
    }

    private String extractSubdomain(String host) {
        if (host == null) return null;
        String[] parts = host.split("\\.");
        if (parts.length < 3) return null; // subdomínio + domínio + tld
        return parts[0]; // pega a primeira parte como subdomínio
    }
}
