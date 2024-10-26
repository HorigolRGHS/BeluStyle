package com.emc.belustyle.rest;

import com.emc.belustyle.dto.NotificationDTO;
import com.emc.belustyle.entity.Notification;
import com.emc.belustyle.service.NotificationService;
import com.emc.belustyle.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public List<Notification> getNotifications() {
        return notificationService.getListNotification();
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/staff")
    @JsonView(Views.ListView.class)
    public ResponseEntity<List<NotificationDTO>> getNotificationsbyStaff() {
        return ResponseEntity.ok(notificationService.getNotificationsByRoleId(3));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    @JsonView(Views.ListView.class)
    public ResponseEntity<List<NotificationDTO>> getNotificationsbyUser() {
        return ResponseEntity.ok(notificationService.getNotificationsByRoleId(2));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getNotificationById(@PathVariable Integer id) {
//        return notificationService.getNotificationById(id);
//    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
        return notificationService.addNotification(notification);
    }

//    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
//    @PutMapping
//    public ResponseEntity<?> updateNotification(@RequestBody Notification notification) {
//        return notificationService.updateNotification(notification);
//    }

    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Integer id) {
        return notificationService.deleteNotification(id);
    }

}
