package com.autohub.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SubdomainInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String host = request.getHeader("Host");

        if (host != null) {
            host = host.split(":")[0];
            String subdomain = host.split("\\.")[0];
            request.setAttribute("subdomain", subdomain);
        }

        return true;
    }
}

