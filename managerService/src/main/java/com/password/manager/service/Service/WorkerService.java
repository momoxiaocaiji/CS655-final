package com.password.manager.service.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WorkerService {

    private static int port = 9000;

    @Value("#{${worker}}")
    private Map<Integer, String> workers;

    private final Map<String, Socket> invokedWorkers = new HashMap<>();

    private static String dec = "wait";

    private void queryWorker(Socket worker) {
        try {
            PrintWriter os = new PrintWriter(worker.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(worker.getInputStream()));

            String msg = "finish";

            os.println(msg);
            os.flush();

            dec = is.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invokeWorker(String enc, int range, int workerNum, String host) {
        //set host & port
        System.out.printf("Worker is running at %s: %s\n", host, port);

        Socket worker = null;

        try {
            // Create a socket
            worker = new Socket(host, port);
            PrintWriter os = new PrintWriter(worker.getOutputStream());

            // message format
            // total worker num + invoked worker index + encryption
            String msg = workerNum + " " + range + " " + enc;

            os.println(msg);
            os.flush();

            os.close();

            invokedWorkers.put(host, worker);
        } catch (IOException e) {
            System.out.println("Worker terminates the connection abnormally!!");
        }
    }

    public String startWork(String enc, int workNum) {
        // invoke workers
        try {
            for (int i = 1; i <= workNum; i++) {
                int finalI = i;
                Thread crackerThread = new Thread(() -> {
                    invokeWorker(enc, finalI, workNum, workers.get(finalI));
                });
                crackerThread.start();
            }

            // query workers
            do {
                TimeUnit.MILLISECONDS.sleep(100);
                for (Socket socket : invokedWorkers.values()) {
                    Thread crackerThread = new Thread(() -> {
                        queryWorker(socket);
                    });
                    crackerThread.start();
                }
            } while (dec.equals("wait") || dec.equals("error"));

            for (Socket socket : invokedWorkers.values()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dec;
    }
}
