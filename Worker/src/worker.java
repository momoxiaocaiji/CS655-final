import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;

public class worker {
    private static String code;
    private static boolean usethread = false;
    private static char[] alphabet = new char[52];
    private static boolean done = false;
    public static String encryptMD5(String str){
        return DigestUtils.md5Hex(str);
    }
    public static String match(String data, int t, int r){
        String res;
        String tempres;
        int sum = 0;
        for (int i = 0; i < 52; i++){
            for (int j = 0; j < 52; j++){
                for (int x = 0; x < 52; x++){
                    for (int y = 0; y < 52; y++){
                        for (int z = 0; z < 52; z++){
                            if (!done){
                                sum ++;
                                String temp = "";
                                temp += alphabet[i];
                                temp += alphabet[j];
                                temp += alphabet[x];
                                temp += alphabet[y];
                                temp += alphabet[z];
                                System.out.println(sum);
                                if (sum % t == r - 1) {
                                    tempres = encryptMD5(temp);
                                    System.out.println("---------------------");
                                    System.out.println("TEMP: " + temp);
                                    if (tempres.equals(data)){
                                        res = temp;
                                        done = true;
                                        return res;
                                    }
                                }
                            }
                            else{
                                done = false;
                                return "error";
                            }
                        }
                    }
                }
            }
        }
        return "error";
    }
    public static void main(String[] args) throws IOException {
        int port = 9000;   // set corresponding port
        System.out.printf("Worker is running at %s \n", port);
        // Initialize the alphabet list
        for (int i = 0; i < 26; i++){
            alphabet[i] = (char)(65 + i);
        }
        for (int j = 26; j < 52; j++){
            alphabet[j] = (char)(71 + j);
        }
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = null;
                BufferedReader bufferedReader = null;
                PrintWriter printWriter =  null;
                try {
                    socket = serverSocket.accept();
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    printWriter = new PrintWriter(socket.getOutputStream());
                    String data ;
                    while((data = bufferedReader.readLine()) != null){
                        System.out.println("System sends: " + data);
                        String[] tempList = data.split(" ");
                        int lengthofdata = tempList.length;
                        if (lengthofdata == 3){
                            String totalnum = tempList[0];
                            if (Integer.parseInt(totalnum) == 0) {
                                if (done){
                                    printWriter.println(code);
                                    printWriter.flush();
                                    done = false;
                                }
                                else{
                                    printWriter.println("wait");
                                    printWriter.flush();
                                }
                            } else {
                                done = false;
                                String rank = tempList[1];
                                String finalData = tempList[2];
                                    Thread decrypt = new Thread(() -> {
                                        code = match(finalData, Integer.parseInt(totalnum), Integer.parseInt(rank));
//                                done = true;

                                    });
                                    decrypt.start();
                            }


                        }
                        else{
                            done = true;
                        }
                    }

                    //System.err.println("closing connection with client");
                    printWriter.close();
                    bufferedReader.close();
                    socket.close();
                }catch(IOException e){
                    printWriter.println(e.getMessage());
                    printWriter.flush();
                    printWriter.close();
                    bufferedReader.close();
                    socket.close();
                    System.err.println("System has misbehavior!! \n Closing connection with system");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isBusy() {
        return done;
    }

    public void setBusy(boolean busy) {
        this.done = busy;
    }

    public static char[] getAlphabet() {
        return alphabet;
    }

    public static void setAlphabet(char[] alphabet) {
        worker.alphabet = alphabet;
    }
}
