package com.emc.belustyle.rest;


import com.emc.belustyle.dto.ResetPasswordDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.mapper.UserMapper;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.security.TokenGenerator;
import com.emc.belustyle.service.EmailService;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import com.emc.belustyle.util.GoogleUtil;
import com.emc.belustyle.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthRestController(UserService userService, UserRoleService userRoleService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO userDTO) {
        ResponseDTO responseDTO = userService.login(userDTO);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

    @PostMapping("/login-for-staff-and-admin")
    public ResponseEntity<ResponseDTO> loginForStaffAndAdmin(@RequestBody UserDTO userDTO) {
        ResponseDTO responseDTO = userService.loginForStaffAndAdmin(userDTO);
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }

//    @PostMapping("/register")
//    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO, HttpServletRequest request) {
//        ResponseDTO responseDTO = new ResponseDTO();
//        try {
//            if (userService.existsByUsername(userDTO.getUsername())) {
//                throw new CustomException(userDTO.getUsername() + " already exists", HttpStatus.CONFLICT);
//            }
//            if (userService.existsByEmail(userDTO.getEmail())) {
//                throw new CustomException(userDTO.getEmail() + " already exists", HttpStatus.CONFLICT);
//            }
//            User user = UserMapper.INSTANCE.toEntity(userDTO);
//            user.setRole(userRoleService.findById(2));
//            user.setEnable(false);
//
//            User savedUser = userService.create(user);
//
//            // Generate token and hash it
//            String token = TokenGenerator.generateToken();
//            String sessionToken = TokenGenerator.md5Hash(token + savedUser.getUsername());
//
//            // Set the token in a cookie instead of session
//            HttpSession session = request.getSession();
//            session.setAttribute("sessionRegistrationToken", sessionToken);
//            session.setMaxInactiveInterval(7 * 24 * 60 * 60);
//
//            // Generate confirmation link
//            String confirmationLink = "http://localhost:8080/api/auth/confirm-registration/" + savedUser.getUserId() + "?token=" + token;
//
//            // Prepare email content
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
//            String currentDateTime = dateFormat.format(new Date());
//            String currentYear = yearFormat.format(new Date());
//
//            String htmlContent = "<div style=\"font-family: Arial, sans-serif; text-align: center; padding: 20px;\">"
//                    + "<h2 style=\"color: #4CAF50;\">Registration Confirmation</h2>"
//                    + "<p style=\"color: #555; font-size: 16px;\">Hello " + user.getFullName() + ",</p>"
//                    + "<p style=\"color: #555; font-size: 16px;\">Thank you for registering with us! Please click the button below to confirm your registration:</p>"
//                    + "<a href=\"" + confirmationLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #4CAF50; text-decoration: none; border-radius: 5px;\">Confirm Registration</a>"
//                    + "<p style=\"color: #555; font-size: 14px; margin-top: 20px;\">If you didn't register, please ignore this email.</p>"
//                    + "<p style=\"color: #555; font-size: 14px;\">Confirmation sent on: " + currentDateTime + "</p>"
//                    + "<p style=\"color: #999; font-size: 12px;\">&copy; " + currentYear + " EMC Company. All rights reserved.</p>"
//                    + "</div>";
//
//            // Send email
//            emailService.sendHtmlMessage(userDTO.getEmail(), "Confirm Your Registration", htmlContent);
//
//            responseDTO.setStatusCode(HttpStatus.CREATED.value());
//            responseDTO.setMessage("We have sent an email to your email for confirmation, please check it!");
//
//        } catch (CustomException e) {
//            responseDTO.setStatusCode(HttpStatus.CONFLICT.value());
//            responseDTO.setMessage(e.getMessage());
//        }
//        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
//    }



    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (userService.existsByUsername(userDTO.getUsername())) {
                throw new CustomException(userDTO.getUsername() + " already exists", HttpStatus.CONFLICT);
            }
            if (userService.existsByEmail(userDTO.getEmail())) {
                throw new CustomException(userDTO.getEmail() + " already exists", HttpStatus.CONFLICT);
            }

            User user = UserMapper.INSTANCE.toEntity(userDTO);
            user.setRole(userRoleService.findById(2));
            user.setEnable(false);
            User savedUser = userService.create(user);

            // Generate JWT Token with user ID and expiration
            String jwtToken = jwtUtil.generateStringToken(savedUser.getUserId() + "registration", 7*24*60*60*1000);

            String confirmationLink = "http://localhost:8080/api/auth/confirm-registration/" + savedUser.getUsername() + "?token=" + jwtToken;


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            String currentDateTime = dateFormat.format(new Date());
            String currentYear = yearFormat.format(new Date());

            String htmlContent = "<div style=\"font-family: Arial, sans-serif; text-align: center; padding: 20px;\">"
                    + "<h2 style=\"color: #4CAF50;\">Registration Confirmation</h2>"
                    + "<p style=\"color: #555; font-size: 16px;\">Hello " + user.getFullName() + ",</p>"
                    + "<p style=\"color: #555; font-size: 16px;\">Thank you for registering with us! Please click the button below to confirm your registration:</p>"
                    + "<a href=\"" + confirmationLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #4CAF50; text-decoration: none; border-radius: 5px;\">Confirm Registration</a>"
                    + "<p style=\"color: #555; font-size: 14px; margin-top: 20px;\">If you didn't register, please ignore this email.</p>"
                    + "<p style=\"color: #555; font-size: 14px;\">Confirmation sent on: " + currentDateTime + "</p>"
                    + "<p style=\"color: #999; font-size: 12px;\">&copy; " + currentYear + " EMC Company. All rights reserved.</p>"
                    + "</div>";

            emailService.sendHtmlMessage(userDTO.getEmail(), "Confirm Your Registration", htmlContent);

            responseDTO.setStatusCode(HttpStatus.CREATED.value());
            responseDTO.setMessage("We have sent an email to your email for confirmation, please check it!");

        } catch (CustomException e) {
            responseDTO.setStatusCode(HttpStatus.CONFLICT.value());
            responseDTO.setMessage(e.getMessage());
        }
        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }


//    @GetMapping("/confirm-registration/{userId}")
//    public void confirmRegistration(
//            @PathVariable String userId,
//            @RequestParam String token,
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//
//        User user = userService.findById(userId);
//
//        HttpSession session = request.getSession();
//        String realToken = (String) session.getAttribute("sessionRegistrationToken");
//        System.out.println(realToken);
//
//        if (TokenGenerator.md5Hash(token + user.getUsername()).equals(realToken)) {
//            user.setEnable(true);
//            userService.updateUser(user);
//            session.removeAttribute("sessionRegistrationToken");
//            response.sendRedirect("http://localhost:3000/confirm-registration/success");
////            return ResponseEntity.ok().body("Okay");
//        }
//        response.sendRedirect("http://localhost:3000/confirm-registration/error");
////        return ResponseEntity.badRequest().body("NOT OKAY ------- ");
//
//    }

    @GetMapping("/confirm-registration/{username}")
    public void confirmRegistration(
            @PathVariable String username,
            @RequestParam String token,
            HttpServletResponse response) throws IOException {

            String userId = userService.findByUsername(username).getUserId();

            // Parse the token
            String parsedUserId = jwtUtil.extractSubject(token);

            // Check if the user ID from the token matches the provided user ID
            if (parsedUserId.equals(userId + "registration")) {
                // Enable the user
                User user = userService.findById(userId);
                user.setEnable(true);
                userService.updateUser(user);
                response.sendRedirect("http://localhost:3000/confirm-registration/success");
//            return ResponseEntity.ok("Registration confirmed successfully");
            }else{
                response.sendRedirect("http://localhost:3000/confirm-registration/error");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
            }


    }



    @GetMapping("/google-callback")
    public ResponseEntity<ResponseDTO> handleGoogleLoginSuccess(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getParameter("token");
        Map<String, String> googleInfo = new GoogleUtil().getGoogleInfoFromToken(token);

        String email = googleInfo.get("email");
        String googleId = googleInfo.get("google_id");
        String fullName = googleInfo.get("name");
        String userImage = googleInfo.get("picture");

        // Call the service method
        ResponseDTO responseDTO = userService.handleGoogleLogin(googleId, email, fullName, userImage);


        return ResponseEntity.status(responseDTO.getStatusCode()).body(responseDTO);
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        String email = resetPasswordDTO.getEmail();
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }

        String jwtToken = jwtUtil.generateStringToken(user.getUserId() + "forgot-password", 15*60*1000);

        // Send email with the reset link
        String resetLink = "http://localhost:3000/reset-password?email=" + email + "&token=" + jwtToken;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String currentDateTime = dateFormat.format(new Date());
        String currentYear = yearFormat.format(new Date());

        String emailContent = "<div style=\"font-family: Arial, sans-serif; text-align: center; padding: 20px;\">"
                + "<h2 style=\"color: #4CAF50;\">Password Reset Request</h2>"
                + "<p style=\"color: #555; font-size: 16px;\">Hello " + user.getFullName() + ",</p>"
                + "<p style=\"color: #555; font-size: 16px;\">You have requested to reset your password. Please click the button below to reset your password:</p>"
                + "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: white; background-color: #4CAF50; text-decoration: none; border-radius: 5px;\">Reset Password</a>"
                + "<p style=\"color: #555; font-size: 14px; margin-top: 20px;\">If you did not request a password reset, please ignore this email.</p>"
                + "<p style=\"color: #555; font-size: 14px;\">Request sent on: " + currentDateTime + "</p>"
                + "<p style=\"color: #999; font-size: 12px;\">&copy; " + currentYear + " EMC Company. All rights reserved.</p>"
                + "</div>";

        emailService.sendHtmlMessage(email, "Reset Password", emailContent);

        return ResponseEntity.ok("Password reset link sent to your email.");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request) {

        User user = userService.findByEmail(resetPasswordDTO.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }

        // Forgot Password
        if (resetPasswordDTO.getOldPassword() == null) {
            if(jwtUtil.isTokenExpired(resetPasswordDTO.getToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your token was expired");
            }

            String parsedUserId = jwtUtil.extractSubject(resetPasswordDTO.getToken());

           if(parsedUserId.equals(user.getUserId() + "forgot-password")) {
               user.setPasswordHash(resetPasswordDTO.getNewPassword());
               userService.updateUser(user);
               return ResponseEntity.ok("Your password has been reset");
           }

        } else { // Reset Password
            PasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
            if (encoder.matches(resetPasswordDTO.getOldPassword(), user.getPasswordHash())) {

                user.setPasswordHash(resetPasswordDTO.getNewPassword());
                userService.updateUser(user);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                String currentDateTime = dateFormat.format(new Date());
                String currentYear = yearFormat.format(new Date());

                // Nội dung email thông báo
                String emailContent = "<div style=\"font-family: Arial, sans-serif; text-align: center; padding: 20px;\">"
                        + "<h2 style=\"color: #4CAF50;\">Password Reset Confirmation</h2>"
                        + "<p style=\"color: #555; font-size: 16px;\">Hello " + user.getFullName() + ",</p>"
                        + "<p style=\"color: #555; font-size: 16px;\">Your password has been successfully reset. You can now log in with your new password.</p>"
                        + "<p style=\"color: #555; font-size: 14px; margin-top: 20px;\">If you did not reset your password, please contact support immediately.</p>"
                        + "<p style=\"color: #555; font-size: 14px;\">Request processed on: " + currentDateTime + "</p>"
                        + "<p style=\"color: #999; font-size: 12px;\">&copy; " + currentYear + " EMC Company. All rights reserved.</p>"
                        + "</div>";

                // Gửi email
                emailService.sendHtmlMessage(resetPasswordDTO.getEmail(), "Password Reset Confirmation", emailContent);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password is incorrect");
            }
        }

        return ResponseEntity.ok("Password reset successful");
    }




}

