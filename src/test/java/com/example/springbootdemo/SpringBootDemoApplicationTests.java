package com.example.springbootdemo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoSpringApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Container
    private static final GenericContainer<?> myAppDev = new GenericContainer<>("myapp:1.1")
            .withExposedPorts(8080);
    @Container
    private static final GenericContainer<?> myAppProd = new GenericContainer<>("myapp:1.2")
            .withExposedPorts(8081);

    @BeforeEach
    void setUp() {
        myAppDev.start();
        myAppProd.start();
    }

    @Test
    void contextLoads() {
        Integer devApp = myAppDev.getMappedPort(8080);
        Integer prodApp = myAppProd.getMappedPort(8081);

        ResponseEntity<String> entityFromDev = restTemplate.getForEntity("http://localhost:" + devApp + "/profile", String.class);
        ResponseEntity<String> entityFromProd = restTemplate.getForEntity("http://localhost:" + prodApp + "/profile", String.class);

        assertEquals("" ,entityFromDev.getBody(), "Current profile is dev");
        assertEquals("", entityFromProd.getBody(), "Current profile is production");


        System.out.println("Dev: " + entityFromDev.getBody());
        System.out.println("Prod: " + entityFromProd.getBody());
    }
}

