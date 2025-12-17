package com.autohub.api.controller.loginController;

import com.autohub.api.controller.loginController.dto.CompleteRegistrationRequest;
import com.autohub.api.controller.loginController.dto.GoogleLoginRequest;
import com.autohub.api.repository.BusinessRepository;
import com.autohub.api.repository.entity.Business;
import com.autohub.api.service.BusinessSeedService;
import com.autohub.api.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final BusinessService businessService;
    private final BusinessRepository businessRepository;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> loginPage() throws Exception {
        return ResponseEntity.ok(loadHtml("static/login.html"));
    }

    @GetMapping(value = "/register", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> registerPage() throws Exception {
        return ResponseEntity.ok(loadHtml("static/register.html"));
    }

    private String loadHtml(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        return StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
    }

    @PostMapping("/google")
    public ResponseEntity<Void> loginGoogle(@RequestBody GoogleLoginRequest request) {

        String token = request.getToken();

        boolean exists = businessRepository
                .findByGoogleTokenHash(token)
                .isPresent();

        if (exists) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/dashboard")
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/login/register")
                .build();
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeRegistration(
            @RequestBody CompleteRegistrationRequest request
    ) {

        boolean exists = businessRepository
                .findByGoogleTokenHash(request.getToken())
                .isPresent();

        if (exists) {
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/dashboard")
                    .build();
        }

        businessService.createBusiness(request.getBusinessName(), request.getToken(), request.getWhatsapp());

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/")
                .build();
    }
}
