/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.captainnobita.oauth2.server.security.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    @Autowired
    private OAuth2Properties oAuth2Properties;
    
    public String generateToken(String clientId) {
        try {
            // 1. Tạo JWT Claims
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(clientId) // Định danh client
                    .issuer(oAuth2Properties.getIssuer()) // Máy chủ phát hành token
                    .expirationTime(new Date(System.currentTimeMillis() + oAuth2Properties.getTokenLifetime() * 1000)) // Hết hạn sau 1 giờ
                    .build();

            // 2. Tạo khóa bí mật
            JWSSigner signer = new MACSigner(oAuth2Properties.getJwtSecret().getBytes());

            // 3. Ký JWT
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256), // Thuật toán ký
                    claims
            );

            signedJWT.sign(signer);

            // 4. Trả về chuỗi JWT
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating JWT", e);
        }
    }
}
