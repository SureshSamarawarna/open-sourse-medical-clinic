package lk.ijse.dep9.clinic.misc;

import org.apache.commons.codec.digest.DigestUtils;

public class CryptoUtil {

    public static void main(String[] args) {
        String[] passwords = {"admin", "doc123", "doc456", "rec123", "rec456"};
        for (String password : passwords) {
            System.out.printf("%s: %s\n", password, getSha256Hex(password));
        }
    }

    public static String getSha256Hex(String input){
        return DigestUtils.sha256Hex(input);
    }
}
