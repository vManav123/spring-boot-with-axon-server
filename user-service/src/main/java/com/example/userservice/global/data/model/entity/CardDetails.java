package com.example.userservice.global.data.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "card_details")
public class CardDetails {
    @Id
    @Column(name = "card_number")
    private String cardNumber;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    private String cardHolderName;
    private Integer cvv;
    private String bankName;
    private boolean active;
    private LocalDateTime expiry;
}
