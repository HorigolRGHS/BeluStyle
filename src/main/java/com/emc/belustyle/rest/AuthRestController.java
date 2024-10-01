package com.emc.belustyle.rest;


import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.security.TokenGenerator;
import com.emc.belustyle.service.EmailService;
import com.emc.belustyle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @Autowired
    public AuthRestController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPasswordHash())
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optionally: Return some user info or a JWT token
            return ResponseEntity.ok("Login successful");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ResponseEntity.ok("User is authenticated");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
    }


    @Autowired
    private EmailService emailService;


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserDTO userDTO) {
        // Kiểm tra email trong cơ sở dữ liệu
        User user = userService.findByEmail(userDTO.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        // Tạo mã khôi phục
        String token = TokenGenerator.generateToken();

        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        String htmlContent = "<p>Click the link to reset your password:</p>"
                + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>";
        emailService.sendHtmlMessage(userDTO.getEmail(), "Reset Password", htmlContent);


        return ResponseEntity.ok(token);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserDTO userDTO) {
        User user = userService.findByEmail(userDTO.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found.");
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
        user.setPasswordHash(encoder.encode(userDTO.getPasswordHash()));
        userService.updateUser(user);
        return ResponseEntity.ok("Password reset successful");
    }


}


