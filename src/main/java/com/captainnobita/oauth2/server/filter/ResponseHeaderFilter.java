/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.captainnobita.oauth2.server.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author huynx
 */
@Component
@Slf4j
public class ResponseHeaderFilter extends GenericFilterBean {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {

        ServletResponseWrapperCopier capturingResponseWrapper = new ServletResponseWrapperCopier(
                (HttpServletResponse) response);

        try {
            fc.doFilter(request, capturingResponseWrapper);
        } finally {
            String responseBody = capturingResponseWrapper.getCaptureAsString();
            log.info("Tra ve response:{}", responseBody);
            response.getOutputStream().write(responseBody.getBytes());
        }
    }
}
