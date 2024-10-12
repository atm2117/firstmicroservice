package com.arnoldmicro.notification;

import com.arnoldmicro.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/v1/notification")
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping
    public void sendNotification(NotificationRequest notificationRequest){
        log.info("New notification... {}", notificationRequest);
        notificationService.sendNotification(notificationRequest);
    }
}
