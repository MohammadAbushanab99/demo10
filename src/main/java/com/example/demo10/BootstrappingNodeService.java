package com.example.demo10;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/newUser")
@CrossOrigin(origins = "*")
public class BootstrappingNodeService {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        // Check user credentials and perform authentication
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        System.out.println(userId);
        System.out.println(password);
        System.out.println("hiiiiiiiiiiiii");
        // Replace this with your authentication logic (e.g., database validation)
        if ("yourUserId".equals(userId) && "yourPassword".equals(password)) {
            // Authentication successful
            String token = "yourAuthToken"; // Replace with a generated token
            String userType = "admin";  // Replace with the user's type
            TokenService tokenService = new TokenService();
            String authToken = tokenService.generateAuthToken();
          //  return ResponseEntity.ok(authToken);
            // Return the token and user type in the response
            LoginResponse response = new LoginResponse(authToken, userType);
            ObjectMapper objectMapper = new ObjectMapper();

            String requestBody = objectMapper.writeValueAsString(response);
            System.out.println(requestBody);
            return ResponseEntity.ok(requestBody);
        } else {
            // Authentication failed
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
}
