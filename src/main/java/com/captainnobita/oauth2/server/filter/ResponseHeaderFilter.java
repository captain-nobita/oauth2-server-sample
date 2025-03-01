/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.captainnobita.oauth2.server.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 *
 * @author huynx
 */
@Component
@Slf4j
public class ResponseHeaderFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {

        HttpServletResponse httpServeletResponse = (HttpServletResponse) response;
        
        HttpServletRequest httpServeletRequest = (HttpServletRequest)request;
        String uri = httpServeletRequest.getRequestURI(); 
        
        Enumeration<String> headerNames = httpServeletRequest.getHeaderNames();
        List<String> listHeader = new ArrayList<>();
        
        // Kiểm tra nếu có header
        if (httpServeletRequest != null) {
            // Duyệt qua từng header và log thông tin
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = httpServeletRequest.getHeader(headerName);
                listHeader.add(headerName + ": " + headerValue);
            }
        }
        
        log.info("Uri:{} headers:[{}]", uri, String.join(", ", listHeader));
        
        // Wrap the request and response to capture content
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServeletRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpServeletResponse);

        try {
            fc.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            String requestBody = new String(wrappedRequest.getContentAsByteArray());
            String responseBody = new String(wrappedResponse.getContentAsByteArray());
            log.info("Request URI:{} httpCode:{} requestBody:{} responseBody:{}", 
                    uri, httpServeletResponse.getStatus(), requestBody, responseBody);
            
            // Giữ lại dữ liệu trong response để trả về cho client
            wrappedResponse.copyBodyToResponse();
        }
    }
}
