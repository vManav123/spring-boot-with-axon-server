package com.example.commonservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDetailsModel {
    private String cardHolderName;
    private String cardNumber;
    private Integer cvv;
    private String bankName;
    private boolean active;
    private LocalDateTime expiry;
}
