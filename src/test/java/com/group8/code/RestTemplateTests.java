package com.group8.code;

import com.group8.code.domain.User;
import com.group8.code.service.impl.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RestTemplateTests {
    @Autowired
    private RestClient restClient;

    @Test
    void connection(){
        ResponseEntity<String> response = restClient.get("/", String.class);
        String content = response.getBody(); // 获取响应内容
        HttpStatusCode statusCode = response.getStatusCode();// 获取HTTP状态码

        System.out.println("Content: " + content);
        System.out.println("Status: " + statusCode.value());
    }

    @Test
    void post(){
        User user = User.builder().firstName("hello").lastName("world").build();
        Map<String,Object> map = new HashMap<>();
        map.put("data",user);
        map.put("vehicule","vehiculo");
        ResponseEntity<String> response = restClient.post("/vehicle/register", map,String.class);
        String content = response.getBody(); // 获取响应内容
        HttpStatusCode statusCode = response.getStatusCode();// 获取HTTP状态码
        System.out.println("Content: " + content);
        System.out.println("Status: " + statusCode.value());



    }

}
