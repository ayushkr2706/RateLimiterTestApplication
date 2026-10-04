package com.ayush.RateLimiterTestApp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/request")
public class testController {

    @PostMapping
    public ResponseEntity<String> getProfile(){
        return ResponseEntity.status(HttpStatus.OK).body("Request Made to the test app");
    }
}
