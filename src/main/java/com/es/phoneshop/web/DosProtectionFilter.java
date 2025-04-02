package com.es.phoneshop.web;

import com.es.phoneshop.model.services.DefaultDosProtectionService;
import com.es.phoneshop.model.services.DosProtectionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

public class DosProtectionFilter extends HttpFilter {
    private DosProtectionService dosProtectionService;

    @Override
    public void init() throws ServletException {
        super.init();
        dosProtectionService = DefaultDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = httpRequest.getRemoteAddr();
        if (dosProtectionService.isAllowed(ip, LocalDateTime.now())) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(429);
        }
    }

}

