/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.captainnobita.oauth2.server.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author Nguyen Xuan Huy <captainnobita@gmail.com>
 */
@Data
public class TestMsgResponse {
    private LocalDateTime clientDatetime;
    private LocalDateTime serverDatetime;
}
