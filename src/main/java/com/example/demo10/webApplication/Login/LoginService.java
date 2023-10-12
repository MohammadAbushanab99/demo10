package com.example.demo10.webApplication.Login;


import com.example.demo10.webApplication.Token.TokenService;
import com.example.demo10.webApplication.DAO.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/newUser")
@CrossOrigin(origins = "*")
public class LoginService {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws IOException, InterruptedException {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();

        if (UserDao.checkUser(userId,password)) {
            String userType = "";
            if(userId.equals("admin")) {
                userType = "admin";
            }else
                userType = "user";

            TokenService tokenService = new TokenService();
            String authToken = tokenService.generateAuthToken()+"||"+userId;
            System.out.println(authToken);
            LoginResponse response = new LoginResponse(authToken, userType);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(response);
            return ResponseEntity.ok(requestBody);
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

}
