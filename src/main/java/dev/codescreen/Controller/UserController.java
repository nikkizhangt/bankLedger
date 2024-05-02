package dev.codescreen.Controller;

import dev.codescreen.Service.UserService;
import dev.codescreen.Entity.*;
import dev.codescreen.Entity.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        try {
            LocalDateTime serverTime = LocalDateTime.now();
            Ping response = new Ping(serverTime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Return a 500 Internal Server Error response
            Error error = new Error(e.getMessage(), "500");
            return ResponseEntity.status(500).body(error);
        }
    }
    @PutMapping("/load")
    public ResponseEntity<?> loadFunds(@RequestBody LoadRequest request) {
        try {
            // Call the UserService to load funds and get a response
            LoadResponse response = userService.loadFunds(request);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            // Return a 500 Internal Server Error response
            Error error = new Error(e.getMessage(), "500");
            return ResponseEntity.status(500).body(error);
        }
    }

    @PutMapping("/authorization")
    public ResponseEntity<?> authorizeTransaction(@RequestBody AuthorizationRequest request) {
        try {
            // Call the UserService to authorize the transaction and get a response
            AuthorizationResponse response = userService.authorizeTransaction(request);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            // Return a 500 Internal Server Error response
            Error error = new Error(e.getMessage(), "500");
            return ResponseEntity.status(500).body(error);
        }
    }


}
