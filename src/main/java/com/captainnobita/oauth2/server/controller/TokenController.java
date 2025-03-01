/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.captainnobita.oauth2.server.controller;

import com.captainnobita.oauth2.server.security.services.JwtUtils;
import com.captainnobita.oauth2.server.security.services.OAuth2Properties;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/oauth2")
public class TokenController {
    
    @Autowired
    private OAuth2Properties oauth2ClientProperties;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/token")
    public Map<String, String> generateTokenUseParam(@RequestParam String client_id, @RequestParam String client_secret) throws InterruptedException {
        // Tìm client theo client_id
        Optional<OAuth2Properties.Client> clientOptional = oauth2ClientProperties.getClients().stream()
                .filter(client -> client.getClientId().equals(client_id))
                .findFirst();

        if (clientOptional.isPresent()) {
            OAuth2Properties.Client client = clientOptional.get();

            // Kiểm tra client_secret (so sánh với BCrypt hash)
            if (passwordEncoder.matches(client_secret, client.getClientSecret())) {
                // Tạo JWT
                String token = jwtUtils.generateToken(client_id); // Triển khai JwtUtils như hướng dẫn trước đó

                // Phản hồi Access Token
                Map<String, String> response = new HashMap<>();
                response.put("access_token", token);
                response.put("token_type", "Bearer");
                response.put("expires_in", String.valueOf(oauth2ClientProperties.getTokenLifetime()));
                
                //Test
                Thread.sleep(500);
                return response;
            } else {
                throw new RuntimeException("Invalid client_secret");
            }
        } else {
            throw new RuntimeException("Invalid client_id");
        }
    }
}
