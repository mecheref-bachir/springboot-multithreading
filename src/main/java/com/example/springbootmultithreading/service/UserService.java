package com.example.springbootmultithreading.service;

import com.example.springbootmultithreading.entity.User;
import com.example.springbootmultithreading.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
   Logger logger = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<User>> saveUser (MultipartFile file) throws Exception {
    long start = System.currentTimeMillis();
    List<User>users =  parseCSVFile(file);
    logger.info("saving lis of users of size {}",users.size());
    logger.info("saving list of user by " + Thread.currentThread().getName());
    users=userRepository.saveAll(users);
    long end = System.currentTimeMillis();
    logger.info("total time {}", end - start);
    return CompletableFuture.completedFuture(users);

    }
    @Async
    public CompletableFuture<List<User>> findAllUsers(){
        logger.info("get list of user by " + Thread.currentThread().getName());
        List<User> users=userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

  private List<User> parseCSVFile(MultipartFile file) throws Exception {

        List<User> users=new ArrayList<>();

        try {
            try(BufferedReader br =new BufferedReader(new InputStreamReader(file.getInputStream()))){
                String line;
                while ((line= br.readLine()) != null){
                    String[] data =line.split(",");
                    User user =new User();
                    user.setGender(data[0]);
                    user.setEmail(data[1]);
                    user.setName(data[2]);
                    users.add(user);


                }
                return  users;
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
            throw  new Exception("failed to parse CSV file");
        }
  }

}
