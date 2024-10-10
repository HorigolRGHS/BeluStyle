package com.emc.belustyle.service;

import com.emc.belustyle.dto.UserIdNameDTO;
import com.emc.belustyle.repo.UserRepository;
import com.emc.belustyle.repo.UserRoleRepository;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.dto.mapper.UserMapper;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.exception.CustomException;
import com.emc.belustyle.util.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserMapper userMapper, UserRoleService userRoleService, EmailService emailService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
        this.emailService = emailService;
    }


    public ResponseDTO login(UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            var user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

            if (!user.isEnabled()) {
                throw new CustomException("User account is disabled. Please contact Belucom204@outlook.com for support.", HttpStatus.FORBIDDEN);
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPasswordHash()));

            if (user.getGoogleId() != null) {
                throw new CustomException("Please login with Google account", HttpStatus.FORBIDDEN);
            }

            var token = jwtUtil.generateUserToken(user);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setToken(token);
            responseDTO.setExpirationTime("1 Day");
            responseDTO.setMessage("Successful");

        } catch (CustomException e) {
            responseDTO.setStatusCode(e.getStatusCode());
            responseDTO.setMessage(e.getMessage());
        } catch (BadCredentialsException e) {
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            responseDTO.setMessage("Your password is incorrect");
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage("An unexpected error occurred during login: " + e.getMessage());
        }
        return responseDTO;
    }

    public ResponseDTO loginForStaffAndAdmin(UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO();



        try {
            var user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

            if (!user.isEnabled()) {
                throw new CustomException("User account is disabled. Please contact Belucom204@outlook.com for support.", HttpStatus.FORBIDDEN);
            }

            if(!user.getRole().getRoleName().equals("STAFF") || !user.getRole().getRoleName().equals("ADMIN") ) {
                throw new CustomException("You have no right to login", HttpStatus.FORBIDDEN);
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPasswordHash()));

            if(user.getGoogleId() != null) {throw new CustomException("Please login with Google account", HttpStatus.FORBIDDEN);}

            var token = jwtUtil.generateUserToken(user);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setToken(token);
            responseDTO.setExpirationTime("1 Day");
            responseDTO.setMessage("Successful");

        } catch (CustomException e) {
            responseDTO.setStatusCode(e.getStatusCode());
            responseDTO.setMessage(e.getMessage());
        } catch (BadCredentialsException e) {
            responseDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            responseDTO.setMessage("Your password is incorrect");
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage("An unexpected error occurred during login: " + e.getMessage());
        }
        return responseDTO;
    }



    public User findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId).orElse(null);
    }

    public User create(User user) {
        PasswordEncoder encode = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
        user.setPasswordHash(encode.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public ResponseDTO handleGoogleLogin(String googleId, String email, String fullName, String userImage) {
        User user = findByGoogleId(googleId);
        String password = email.substring(0, email.indexOf("@")) + email.length(); // Generate a password

        if (user == null) {
            user = new User();
            user.setGoogleId(googleId);
            user.setUsername(email);
            user.setPasswordHash(password);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setUserImage(userImage);
            user.setRole(userRoleService.findById(2)); // Assuming role ID 2 is for users
            user.setEnable(true);
            create(user); // Save user
        }
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), password));
            var userCheck = userRepository.findByUsername(email).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
            var token = jwtUtil.generateUserToken(userCheck);

            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setToken(token);
            responseDTO.setExpirationTime("1 Day");
            responseDTO.setMessage("Successful");
        } catch (CustomException e) {
            responseDTO.setStatusCode(e.getStatusCode());
            responseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDTO.setMessage("Error During  " + e.getMessage());
        }
        return responseDTO;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User updateUser(User updatedUser) {
        Optional<User> existingUser = userRepository.findById(updatedUser.getUserId());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFullName(updatedUser.getFullName());
            PasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 10);
            user.setPasswordHash(encoder.encode(updatedUser.getPasswordHash()));
            user.setCurrentPaymentMethod(updatedUser.getCurrentPaymentMethod());
            user.setUserImage(updatedUser.getUserImage());
            user.setUserAddress(updatedUser.getUserAddress());
            user.setEnable(updatedUser.getEnable());
            return userRepository.save(user);
        } else {
            return null;
        }
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public List<UserIdNameDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserIdNameDTO(user.getUserId(), user.getFullName()))
                .collect(Collectors.toList());
    }


    public Page<ViewUserDTO> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> new ViewUserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getEnable(),
                user.getCreatedAt(),
                user.getUpdatedAt()));
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}










