package com.example.orderservice.global.data.model.rest;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {
	private String error;
	private LocalDateTime timestamp;
	private HttpStatus status;
	private int code;
}
