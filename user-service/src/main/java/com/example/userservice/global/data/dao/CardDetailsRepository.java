package com.example.userservice.global.data.dao;

import com.example.userservice.global.data.model.entity.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails,String> {
}
