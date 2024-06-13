package com.group8.code;

import com.group8.code.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class CodeApplicationTests {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Test
    void contextLoads() {
    }

    @Test
    public void TestBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("abc1234");
        //$2a$10$dih5fPzDN9gJe7QxNDgpsuu25Z6f1Nd.PTlPStlI5jqjQtVbQVS8G
        System.out.println(encode);

    }

    @Test
    public void decode(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.matches("abc1234","$2a$10$dih5fPzDN9gJe7QxNDgpsuu25Z6f1Nd.PTlPStlI5jqjQtVbQVS8G"));
    }

    @Test
    void listAppointments(){
        System.out.println(appointmentRepository.findAll());
    }

}
