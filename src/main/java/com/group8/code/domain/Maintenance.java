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
@Document("maintenances")
public class Maintenance {
    @Id
    private String id;
    private String status;
    private String date;
    private String description;
    private List<Detail> details;

    private String employeeId;
    private String vehicleId;
    private String appointmentId;

    private User employee;
    private Vehicle vehicle;
    private Appointment appointment;
}
