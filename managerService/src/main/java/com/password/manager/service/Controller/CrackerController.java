package com.password.manager.service.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrackerController {


    @PostMapping("/start")
    public String startTheTask(){
        return "Hello World!!!";
    }
}
