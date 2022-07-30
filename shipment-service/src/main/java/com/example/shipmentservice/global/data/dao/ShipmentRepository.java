package com.example.shipmentservice.global.data.dao;

import com.example.shipmentservice.global.data.model.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,String> {
}
