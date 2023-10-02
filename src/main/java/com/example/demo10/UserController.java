package com.example.demo10;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addUser")
@CrossOrigin(origins = "*")
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Perform validation of user input
        if (isValidUser(user)) {
            //userRepository.save(user);
            System.out.println(user.getUserId());
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid user data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = createMockedUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                users.remove(user);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    private List<User> createMockedUsers() {

        if(users.isEmpty())
        // Mocked user data
        {
            User user1 = new User("user1", "User One", "User One", "User One", "User One");
            User user2 = new User("user2", "User Two", "User Two", "User Two", "User Two");
            User user3 = new User("user3", "User Three", "User Two", "User Two", "User Two");

            users.add(user1);
            users.add(user2);
            users.add(user3);

        }
        return users;
    }

    // Implement user input validation logic here
    private boolean isValidUser(User user) {
        // You can add validation logic here
        return true;
    }
}