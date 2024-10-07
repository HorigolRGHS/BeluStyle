package com.emc.belustyle.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
