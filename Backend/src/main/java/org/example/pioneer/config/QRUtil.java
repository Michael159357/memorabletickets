package org.example.pioneer.config;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class QRUtil {

    private final String SECRET_KEY = "daskbdjkwasbcdhqwsjhadlskdjkqvwdjkasdjkqwv"; // Cambia esto a un sistema dinámico en producción

    public String encrypt(String data) throws Exception {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData) throws Exception {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decodedData));
    }
}
