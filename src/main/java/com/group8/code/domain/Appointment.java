package com.group8.code.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("appointments")
public class Appointment {
    @Id
    private String id;
    private String scheduledDate;
    private String status;

    private List<String> requestedServiceIds;
    private String customerId;
    private String vehicleId;

    private List<Servicio> requestedServices;
    private User customer;
    private Vehicle vehicle;
}
