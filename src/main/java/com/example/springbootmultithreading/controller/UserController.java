package com.example.springbootmultithreading.controller;

import com.example.springbootmultithreading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/users")
    public ResponseEntity saveUsers(@RequestParam(name = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            userService.saveUser(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
@GetMapping("/users")
    public CompletableFuture<ResponseEntity> findAllUsers(){
       return  userService.findAllUsers().thenApply(ResponseEntity::ok);
    }

}
