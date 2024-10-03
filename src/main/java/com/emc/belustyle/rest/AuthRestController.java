package com.emc.belustyle.rest;


import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.security.JwtUtil;
import com.emc.belustyle.security.TokenGenerator;
import com.emc.belustyle.service.EmailService;
import com.emc.belustyle.service.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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

            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("The user was disabled");
            }

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optionally: Return some user info or a JWT token
//            return ResponseEntity.ok("Login successful");
            User user = userService.findByUsername(userDTO.getUsername());

            JwtUtil jwtUtil = new JwtUtil();
            String token = jwtUtil.generateUserToken(user);

            return ResponseEntity.ok(token);
            // return

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

    }

    @PostMapping("/loginForManagement")
    public ResponseEntity<String> loginForManagement(@RequestBody UserDTO userDTO) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPasswordHash())
            );

            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("The user was disabled");
            }

            boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals("ROLE_ADMIN") ||
                                    grantedAuthority.getAuthority().equals("ROLE_STAFF"));

            if (!isStaffOrAdmin) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You do not have the necessary role.");
            }

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optionally: Return some user info or a JWT token
//            return ResponseEntity.ok("Login successful");
            User user = userService.findByUsername(userDTO.getUsername());

            JwtUtil jwtUtil = new JwtUtil();
            String token = jwtUtil.generateUserToken(user);

            return ResponseEntity.ok(token);
            // return

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


    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody UserDTO userDTO) {
        // Kiểm tra email trong cơ sở dữ liệu
        User user = userService.findByEmail(userDTO.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        // Generate reset token (You can store this if needed, but you mentioned you don't want to)
        String resetToken = TokenGenerator.generateToken();

        JwtUtil jwtUtil = new JwtUtil();
        String jwtToken = jwtUtil.generateTokenWithResetToken(resetToken);


        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String currentDateTime = dateFormat.format(new Date());
        String currentYear = yearFormat.format(new Date());

        String htmlContent = "<div style=\"font-family: Arial, sans-serif; text-align: center; padding: 20px;\">"
                + "<h2 style=\"color: #4CAF50;\">Password Reset Request</h2>"
                + "<p style=\"color: #555; font-size: 16px;\">Hello " + user.getFullName() + ",</p>"
                + "<p style=\"color: #555; font-size: 16px;\">We received a request to reset your password. Please click the button below to reset your password:</p>"
                + "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #4CAF50; text-decoration: none; border-radius: 5px;\">Reset Password</a>"
                + "<p style=\"color: #555; font-size: 14px; margin-top: 20px;\">If you didn't request this, please ignore this email.</p>"
                + "<p style=\"color: #555; font-size: 14px;\">Request sent on: " + currentDateTime + "</p>" // Thêm thời gian
                + "<p style=\"color: #999; font-size: 12px;\">&copy; " + currentYear + " EMC Company. All rights reserved.</p>"
                + "</div>";
        emailService.sendHtmlMessage(userDTO.getEmail(), "Reset Password", htmlContent);


        return ResponseEntity.ok(jwtToken);
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

    @GetMapping("/google")
    public ResponseEntity<String> getUser(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userService.findByEmail(email);

        JwtUtil jwtUtil = new JwtUtil();

        return ResponseEntity.ok(jwtUtil.generateUserToken(user));
    }

}




