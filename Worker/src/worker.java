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
        for (int i = (int)((r - 1) * (52 * (r / (double)t))); i < (r * 52 * (r / (double)t)); i++){
            for (int j = 0; j < 52; j++){
                for (int x = 0; x < 52; x++){
                    for (int y = 0; y < 52; y++){
                        for (int z = 0; z < 52; z++){
                            if (!done){
                                String temp = "";
                                temp += alphabet[i];
                                temp += alphabet[j];
                                temp += alphabet[x];
                                temp += alphabet[y];
                                temp += alphabet[z];
                                res = encryptMD5(temp);
                                if (res.equals(data)){
                                    code = res;
                                    done = true;
                                    return code;
                                }
                            }
                            else{
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
        for (int i = 0; i < 27; i++){
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
                        String totalnum = tempList[0];
                        String rank = tempList[1];
                        String finalData = tempList[2];
                        if(!usethread){
                            usethread = true;
                            Thread decrypt = new Thread(() -> {
                                code = match(finalData, Integer.parseInt(totalnum), Integer.parseInt(rank));
//                                done = true;
                                usethread = false;
                            });
                            decrypt.start();
                        }
                        if (done){
                            printWriter.println(code);
                            printWriter.flush();
                        }
                        else{
                            printWriter.println("wait");
                            printWriter.flush();
                        }
                    }

                    System.err.println("closing connection with client");
                    printWriter.close();
                    bufferedReader.close();
//                    socket.close();
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
