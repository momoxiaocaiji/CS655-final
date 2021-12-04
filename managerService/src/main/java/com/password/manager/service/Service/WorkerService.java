package com.password.manager.service.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WorkerService {

    private static int port = 9000;

    @Value("#{${worker}}")
    private Map<Integer, String> workers;

    private final List<String> invokedWorkers = new ArrayList<>();

    private static String dec = "wait";

    private String queryWorker(String enc, int range, int workerNum, String ip) {

        Socket worker = null;
        try {
            // Create a socket
            worker = new Socket(ip, port);
            PrintWriter os = new PrintWriter(worker.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(worker.getInputStream()));

            String msg = workerNum + " " + range + " " + enc;
            //String msg = "finish";

            os.println(msg);
            os.flush();

            String result = is.readLine();

            worker.close();
            os.close();
            is.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "wait";
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
            worker.close();
            os.close();

            invokedWorkers.add(host);
        } catch (IOException e) {
            System.out.println("Worker terminates the connection abnormally!!");
        }
    }

    public String startWork(String enc, int workNum) {
        // invoke workers
        try {
            for (int i = 1; i <= workNum; i++) {
                int finalI = i;
                invokeWorker(enc, finalI, workNum, workers.get(finalI));
            }

            // query workers
            do {
                TimeUnit.MILLISECONDS.sleep(100);
                Iterator<String> ite = invokedWorkers.iterator();
                String str = "";
                while (ite.hasNext()) {
                    str = ite.next();
                    String res = queryWorker(enc, 1, 1, str);
                    if (res.equals("error")) {
                        ite.remove();
                        System.out.println("The worker "+ str + " didn't get the result");
                    } else {
                        dec = res;
                    }
                }
            } while (dec.equals("wait") && invokedWorkers.size() != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dec;
    }
}
