package com.emc.belustyle.rest;

import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.UserIdNameDTO;
import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to view all user details
    @GetMapping("/users")
    public ResponseEntity<List<ViewUserDTO>> getAllUsers() {
        List<ViewUserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (user != null) {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build(); // Trả về mã 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Trả về mã 404 Not Found
        }
    }
    @GetMapping("/users/search/fullname")
    public ResponseEntity<List<UserIdNameDTO>> searchUsersByFullName(@RequestParam String fullName) {
        List<UserIdNameDTO> users = userService.searchUsersByFullName(fullName);
        return ResponseEntity.ok(users);
    }
}
