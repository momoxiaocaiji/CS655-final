package com.password.manager.service.Controller;

import com.password.manager.service.Model.Database;
import com.password.manager.service.Model.Encryption;
import com.password.manager.service.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CrackerController {

    @Autowired
    private WorkerService workerService;

    @Value("#{${worker}}")
    private Map<String, String> maps;

    private static final Database database = new Database();

    @PostMapping("/start")
    public String startTheTask(@RequestBody Encryption encryption) {
        String enc = encryption.getEncryption();

        if (database.getData().containsKey(enc)) {
            return database.getData().get(enc);
        } else {
            workerService.connect("192.168.0.13");
            database.getData().put(enc, "data test");
            return "Hello World!!!";
        }
    }
}
