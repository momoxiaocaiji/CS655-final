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
                                    code = temp;
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
    public static void main(String[] args){
        for (int i = 0; i < 27; i++){
            alphabet[i] = (char)(65 + i);
        }
        for (int j = 26; j < 52; j++){
            alphabet[j] = (char)(71 + j);
        }
        System.out.println(alphabet);
        code = match("f6a6263167c92de8644ac998b3c4e4d1", 1, 1);
        System.out.println(code);
    }

}
