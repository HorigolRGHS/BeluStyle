package com.emc.belustyle.service;


import com.emc.belustyle.repo.UserRepository;
import com.emc.belustyle.repo.UserRoleRepository;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.mapper.UserMapper;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;
    private final UserRoleService userRoleService;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserMapper userMapper, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
    }


    public ResponseDTO login(UserDTO userDTO){
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPasswordHash()));
            var user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new CustomException("User not found"));
            var token = jwtUtil.generateUserToken(user);
            responseDTO.setStatusCode(200);
            responseDTO.setToken(token);
            responseDTO.setExpirationTime("1 Day");
            responseDTO.setMessage("Successful");

        }catch (CustomException e) {
            responseDTO.setStatusCode(400);
            responseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error Occurred During USer Registration " + e.getMessage());
        }
        return responseDTO;
    }



    public ResponseDTO register(UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (userRepository.existsByUsername(userDTO.getUsername())) {
                throw new CustomException(userDTO.getUsername() + " already exists");
            }
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new CustomException(userDTO.getEmail() + " already exists");
            }
            userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
            userDTO.setEnable(true);

            User savedUser = userMapper.toEntity(userDTO);
            savedUser.setRole(userRoleRepository.findById(2).orElse(null));
            userRepository.save(savedUser);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPasswordHash()));
            var user = userRepository.findByUsername(userDTO.getUsername()).orElse(null);
            var token = jwtUtil.generateUserToken(user);

            responseDTO.setStatusCode(200);
            responseDTO.setToken(token);
            responseDTO.setExpirationTime("1 Day");
            responseDTO.setMessage("Successful");
        } catch (CustomException e) {
            responseDTO.setStatusCode(400);
            responseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDTO.setStatusCode(500);
            responseDTO.setMessage("Error Occurred During USer Registration " + e.getMessage());
        }
        return responseDTO;
    }

    public User findbyGoogleId(String googleId){
        return userRepository.findByGoogleId(googleId).orElse(null);
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public User handleGoogleLogin(String googleId, String email, String fullName, String userImage) {
        User user = findbyGoogleId(googleId);
        String password = email + email.length(); // Generate a password

        if (user == null) {
            user = new User();
            user.setGoogleId(googleId);
            user.setUsername(email.substring(0, email.indexOf("@")));
            PasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
            user.setPasswordHash(encoder.encode(password));
            user.setEmail(email);
            user.setFullName(fullName);
            user.setUserImage(userImage);
            user.setRole(userRoleService.findById(2)); // Assuming role ID 2 is for users
            user.setEnable(true);
            create(user); // Save user
        }

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), password));


        return user; // Return the authenticated user
    }
}










