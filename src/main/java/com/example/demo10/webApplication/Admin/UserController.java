package com.example.demo10.webApplication.Admin;

import com.example.demo10.BootstrappingNode.BootstrappingNodeService;
import com.example.demo10.webApplication.DAO.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/addUser")
@CrossOrigin(origins = "*")
public class UserController {



    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) throws IOException, InterruptedException {
        if (isValidUser(user)) {
           // addUserQueue.add(user);
            BootstrappingNodeService bootstrappingNodeService = new BootstrappingNodeService();
            String containerName = bootstrappingNodeService.addNewUser(user.getUserId());
            CreateUser createUser = new CreateUser();
            createUser.createNewUser(user,containerName);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid user data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) throws IOException, InterruptedException {
        String userId = extractTokenFromAuthorizationHeader(authorizationHeader);
        List<User> users = UserDao.getUsers(userId);
        System.out.println("users.isEmpty() = " + users.isEmpty());
        if(!users.isEmpty()){
            for (User user : users) {
                System.out.println(user.getName());
            }
        }
        return ResponseEntity.ok(users);
    }

    private String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        String token = "";
        if (parts.length == 2 && "Bearer".equals(parts[0])) {
            token = parts[1];
        }
        String[] partsToken = token.split("\\|\\|");
        if (partsToken.length >= 2) {
            return partsToken[1];
        }
        return null;
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId,@RequestHeader("Authorization") String authorizationHeader) throws IOException, InterruptedException {
        String userToken = extractTokenFromAuthorizationHeader(authorizationHeader);
        List<User> users = UserDao.getUsers(userToken);
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                UserDao.deleteUser(userId);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }


    private boolean isValidUser(User user) {
        return true;
    }
}