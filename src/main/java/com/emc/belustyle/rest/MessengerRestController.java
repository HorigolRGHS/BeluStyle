package com.emc.belustyle.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messenger")
public class MessengerRestController {
    @PreAuthorize("hasAnyAuthority('CUSTOMER','GUEST')")
    @GetMapping("/redirectToMessenger")
    public ResponseEntity<Void> redirectToMessenger() {
        String messengerUrl = "https://m.me/8386572768104880";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", messengerUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
