    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.captainnobita.oauth2.server.controller;

import com.captainnobita.oauth2.server.dto.TestMsgRequest;
import com.captainnobita.oauth2.server.dto.TestMsgResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Nguyen Xuan Huy <captainnobita@gmail.com>
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${app.sleep-time:10}")
    private long sleepTime;
    
    @PostMapping("/currentDate")
    public ResponseEntity<?> currentDateTime(@RequestBody TestMsgRequest msgRequest) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(sleepTime));
        TestMsgResponse msgResponse = new TestMsgResponse();
        msgResponse.setClientDatetime(msgRequest.getClientDatetime());
        msgResponse.setServerDatetime(LocalDateTime.now());
        return ResponseEntity.badRequest().build();
    }
}
