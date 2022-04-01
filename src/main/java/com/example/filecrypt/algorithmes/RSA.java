package com.example.filecrypt.algorithmes;

import javax.crypto.Cipher;
import java.security.*;

public class RSA {
    private static final String ENCRYPT_ALGO = "RSA/ECB/PKCS1Padding";


    public static byte[] encrypt(byte[] file, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(file);
    }

    public static byte[] decrypt(byte[] file, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(file);
    }


}
