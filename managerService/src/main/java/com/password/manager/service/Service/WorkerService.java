package com.password.manager.service.Service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@Service
public class WorkerService {



    public void connect(String host){
        //set host & port
        int port = 9000;
        System.out.printf("Worker is running at %s: %s\n", host, port);

        Socket client = null;

        try {
            // Create a socket
            client = new Socket(host, port);
            PrintWriter os = new PrintWriter(client.getOutputStream());

            String msg = "Hello worker!";

            os.println(msg);
            os.flush();

            os.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Worker terminates the connection abnormally!!");
        }
    }
}
