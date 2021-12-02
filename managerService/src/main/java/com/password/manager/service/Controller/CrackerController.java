package com.password.manager.service.Controller;

import com.password.manager.service.Model.Database;
import com.password.manager.service.Model.Encryption;
import com.password.manager.service.Service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class CrackerController {

    @Autowired
    private WorkerService workerService;

    private static final Database database = new Database();

    @PostMapping("/start")
    public String startTheTask(@RequestBody Encryption encryption) {
        String enc = encryption.getEncryption();
        if (database.getData().containsKey(enc)) {
            return database.getData().get(enc);
        } else {
            // get idle worker
            String dyn = workerService.startWork(enc, encryption.getWorkNum());
            database.getData().put(enc, dyn);
            return dyn;
        }
    }
}
