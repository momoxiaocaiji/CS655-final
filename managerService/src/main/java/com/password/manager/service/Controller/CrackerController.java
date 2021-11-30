package com.password.manager.service.Controller;

import com.password.manager.service.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CrackerController {

    @Autowired
    private WorkerService workerService;

    @Value("#{${worker}}")
    private Map<String,String> maps;

    @PostMapping("/start")
    public String startTheTask(){
        System.out.println(maps);


        workerService.connect("192.168.0.13");
        return "Hello World!!!";
    }
}
