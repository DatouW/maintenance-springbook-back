package com.group8.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("vehicles")
public class Vehicle {
    @Id
    private String id;
    private String licensePlate;
    private String brand;
    private String model;
    private int year;
    private String vin;
    private String customerId;

    private User customer;
}
