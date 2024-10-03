package com.emc.belustyle.service;


import com.emc.belustyle.dao.UserRepository;
import com.emc.belustyle.dao.UserRoleRepository;
import com.emc.belustyle.dto.UserIdNameDTO;
import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<UserIdNameDTO> searchUsersByFullName(String fullName) {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(fullName);
        return users.stream()
                .map(user -> new UserIdNameDTO(user.getUserId(), user.getFullName()))
                .collect(Collectors.toList());
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Page<ViewUserDTO> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> new ViewUserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getEnable(),
                user.getCreatedAt(),
                user.getUpdatedAt()));
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void createUser(OAuth2User oauth2User) {
        String googleId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String fullName = oauth2User.getAttribute("name");
        String userImage = oauth2User.getAttribute("picture");

        User user = userRepository.findByEmail(email);
        // Check if user already exists
        if (user==null) {
            user = new User();
            user.setUserId(googleId);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setUserImage(userImage);
            UserRole role = userRoleRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
            userRepository.save(user);
        }
    }

    @Transactional
    public User updateUser(User updatedUser) {
        Optional<User> existingUser = userRepository.findById(updatedUser.getUserId());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setFullName(updatedUser.getFullName());
            user.setUserImage(updatedUser.getUserImage());
            user.setEnable(updatedUser.getEnable());
            UserRole role = userRoleRepository.findById(2)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
            user.setCurrentPaymentMethod(updatedUser.getCurrentPaymentMethod());
            user.setUserAddress(updatedUser.getUserAddress());
            user.setUpdatedAt(updatedUser.getUpdatedAt());
            return userRepository.save(user);
        } else {
            return null;
        }
    }



    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
