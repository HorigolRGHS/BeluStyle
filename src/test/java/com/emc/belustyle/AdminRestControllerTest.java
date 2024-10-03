package com.emc.belustyle;

import com.emc.belustyle.dto.ViewUserDTO;
import com.emc.belustyle.rest.AdminRestController;
import com.emc.belustyle.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminRestControllerTest {

    @InjectMocks
    private AdminRestController adminRestController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<ViewUserDTO> userList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            userList.add(new ViewUserDTO("user" + i, "user" + i + "@example.com", true, new Date(), new Date()));
        }

        // Create a Page object from the userList
        Page<ViewUserDTO> page = new PageImpl<>(userList.subList(0, 10), PageRequest.of(0, 10), userList.size());

        // Mock the service call to return the page
        when(userService.getAllUsers(0, 10)).thenReturn(page);

        // Act
        ResponseEntity<Page<ViewUserDTO>> response = adminRestController.getAllUsers(0, 10);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(page, response.getBody());
        verify(userService, times(1)).getAllUsers(0, 10);
    }
}
