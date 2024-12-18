package com.emc.belustyle.service;

import com.emc.belustyle.dto.NotificationDTO;
import com.emc.belustyle.dto.ResponseDTO;
import com.emc.belustyle.entity.Notification;
import com.emc.belustyle.repo.NotificationRepository;
import com.emc.belustyle.repo.UserRepository;
import com.emc.belustyle.repo.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRoleRepository userRoleRepository) {
        this.notificationRepository = notificationRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<Notification> getListNotification() {
        return notificationRepository.findAllByOrderByNotificationIdDesc();
    }

    public ResponseEntity<?> getNotificationById(Integer id) {
        ResponseDTO responseDTO = new ResponseDTO();
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        responseDTO.setMessage("Notification not found");
        return ResponseEntity.status(404).body(responseDTO);
    }

    @Transactional
    public ResponseEntity<?> addNotification(NotificationDTO notificationDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Notification notification = new Notification();
        notification.setTitle(notificationDTO.getTitle());
        notification.setMessage(notificationDTO.getMessage());
        notification.setRole(userRoleRepository.findById(notificationDTO.getTargetRoleId()).orElse(null));
        notificationRepository.save(notification);
        responseDTO.setMessage("Successfully added notification");
        return ResponseEntity.status(200).body(responseDTO);
    }

    @Transactional
    public ResponseEntity<?> deleteNotification(Integer id) {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            notificationRepository.delete(notification.get());
            responseDTO.setMessage("Successfully deleted notification");
            return ResponseEntity.status(200).body(responseDTO);
        }
        responseDTO.setMessage("Notification not found");
        return ResponseEntity.status(404).body(responseDTO);
    }

    public List<NotificationDTO> getNotificationsByRoleId(int roleId) {
       List<Object[]> results = notificationRepository.findByRoleId(roleId);
       List<NotificationDTO> notificationList = new ArrayList<>();

       for (Object[] result : results) {
           NotificationDTO notificationDTO = new NotificationDTO();
           notificationDTO.setTitle((String) result[0]);
           notificationDTO.setMessage((String) result[1]);
           notificationDTO.setCreatedAt((Date) result[2]);
           notificationList.add(notificationDTO);
       }

       return notificationList;
    }

//    @Transactional
//    public ResponseEntity<?> updateNotification(Notification notification) {
//        ResponseDTO responseDTO = new ResponseDTO();
//        Notification updatedNotification = notificationRepository.getById(notification.getNotificationId());
//        if (updatedNotification != null) {
//            updatedNotification.setTitle(notification.getTitle());
//            updatedNotification.setMessage(notification.getMessage());
//            updatedNotification.setRole(notification.getRole());
//            notificationRepository.save(updatedNotification);
//            responseDTO.setMessage("Successfully updated notification");
//            return ResponseEntity.status(200).body(responseDTO);
//        }
//        responseDTO.setMessage("Notification not found");
//        return ResponseEntity.status(404).body(responseDTO);
//    }
}
