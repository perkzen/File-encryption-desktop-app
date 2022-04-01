package com.example.filecrypt.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class CryptoUtils {

    public static byte[] getRandomNonce(int bytes) {
        byte[] nonce = new byte[bytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // key size 128, 192 ,256
    public static SecretKey getAESKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    // 1028 2048
    public static KeyPair generateKeyPair(int bitSize) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(bitSize);
        return generator.generateKeyPair();
    }

}
