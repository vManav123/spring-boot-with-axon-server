package com.example.paymentservice.global.data.dao;

import com.example.paymentservice.global.data.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,String> {
}
