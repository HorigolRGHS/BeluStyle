package com.emc.belustyle;

import com.emc.belustyle.dto.UserDTO;
import com.emc.belustyle.entity.User;
import com.emc.belustyle.entity.UserRole;
import com.emc.belustyle.rest.AdminRestController;
import com.emc.belustyle.service.UserRoleService;
import com.emc.belustyle.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminRestControllerTest {

    @InjectMocks
    private AdminRestController adminRestController; // Controller bạn đang test

    @Mock
    private UserService userService;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private BCryptPasswordEncoder encoder;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Khởi tạo các mock object

        // Tạo đối tượng userDTO để sử dụng trong các bài test
        userDTO = new UserDTO();
        userDTO.setEmail("test@staff.com");
        userDTO.setUsername("testUser");
        userDTO.setFullName("Test User");
        userDTO.setPasswordHash("testPassword");
        userDTO.setRoleId(3);
        userDTO.setUserImage("testImage");
        userDTO.setEnable(true);
    }

    // Test tạo tài khoản staff thành công
    @Test
    public void testCreateUser_Success() {
        User mockUser = new User();
        mockUser.setEmail("test@staff.com");
        mockUser.setUsername("testUser");

        UserRole staffRole = new UserRole();
        staffRole.setRoleId(3);
        staffRole.setRoleName(UserRole.RoleName.valueOf("STAFF"));

        when(userService.findByEmail(anyString())).thenReturn(null);
        when(userRoleService.findById(3)).thenReturn(staffRole);
        when(encoder.encode(anyString())).thenReturn("hashedPassword");

        ResponseEntity<String> response = adminRestController.createUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account staff created successfully!", response.getBody());

        verify(userService, times(1)).createUser(any(User.class)); // Xác nhận createUser được gọi 1 lần
    }

    // Test khi email đã tồn tại
    @Test
    public void testCreateUser_EmailExists() {
        when(userService.findByEmail(anyString())).thenReturn(new User()); // Giả lập email đã tồn tại

        ResponseEntity<String> response = adminRestController.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());

        verify(userService, never()).createUser(any(User.class)); // createUser không được gọi
    }

    // Test khi xảy ra lỗi trong quá trình tạo tài khoản
    @Test
    public void testCreateUser_Exception() {
        when(userService.findByEmail(anyString())).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<String> response = adminRestController.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("An error occurred while creating a staff account.", response.getBody());

        verify(userService, never()).createUser(any(User.class)); // createUser không được gọi
    }
}