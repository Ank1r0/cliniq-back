package com.example.cliniqserv.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
@RequiredArgsConstructor
@CrossOrigin
public class HelloController {
    @GetMapping()
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello");
    }
}