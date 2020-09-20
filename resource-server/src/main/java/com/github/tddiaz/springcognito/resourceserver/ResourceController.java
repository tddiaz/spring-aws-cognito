package com.github.tddiaz.springcognito.resourceserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ResourceController {


    @GetMapping("/resource/greetings")
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public String greetings() {
        return "Hello world";
    }
}
