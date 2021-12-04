import org.apache.commons.codec.digest.DigestUtils;

public class test {
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
        for (int i = (int)((52 * ((r - 1) / (double)t))); i < (52 * (r / (double)t)); i++){
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
                                tempres = encryptMD5(temp);
                                if (tempres.equals(data)){
                                    res = temp;
                                    done = true;
                                    return res;
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
    public static void main(String[] args){
        for (int i = 0; i < 27; i++){
            alphabet[i] = (char)(65 + i);
        }
        for (int j = 26; j < 52; j++){
            alphabet[j] = (char)(71 + j);
        }
        System.out.println(alphabet);
        code = match("8f113e38d28a79a5a451b16048cc2b72", 1, 1);
        System.out.println(code);
    }

}
