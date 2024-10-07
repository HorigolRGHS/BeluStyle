package com.emc.belustyle.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @GetMapping("/oauth2/authorization/google")
    public String googleLogin() {
        return "redirect:/oauth2/authorization/google";
    }

}
