package com.example.filecrypt.algorithmes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;

public class AES {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;


    public static byte[] encrypt(byte[] fileBytes, SecretKey secret, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return cipher.doFinal(fileBytes);
    }

    public static byte[] encryptWithPrefixIV(byte[] fileBytes, SecretKey secret, byte[] iv) throws Exception {
        byte[] cipherText = encrypt(fileBytes, secret, iv);
        return ByteBuffer.allocate(iv.length + cipherText.length)
                .put(iv)
                .put(cipherText)
                .array();
    }

    public static byte[] decrypt(byte[] fileBytes, SecretKey secret, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        return cipher.doFinal(fileBytes);
    }

    public static byte[] decryptWithPrefixIV(byte[] fileBytes, SecretKey secret) throws Exception {
        ByteBuffer bb = ByteBuffer.wrap(fileBytes);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);
        return decrypt(cipherText, secret, iv);
    }
}
