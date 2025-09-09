package com.ChatApp.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Component
@Slf4j
public class MessageEncrypt {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final Cipher cipher;

    public MessageEncrypt() {
        String publicPath = "src/main/resources/rsa/public.pem";
        String privatePath = "src/main/resources/rsa/private.pem";
        String publicVal = standardizeKey(publicPath);
        String privateVal = standardizeKey(privatePath);
        log.info("publicVal: {}", publicVal);
        log.info("privateVal: {}", privateVal);
        byte[] publicBytes = Base64.getDecoder().decode(publicVal);
        byte[] privateBytes = Base64.getDecoder().decode(privateVal);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
            this.publicKey = kf.generatePublic(new X509EncodedKeySpec(publicBytes));
            this.cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            log.error("Error when initializing MessageEncrypt: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public String standardizeKey(String path){
        try {
            String content = Files.readString(Path.of(path));
            return content.replaceAll("\\s+", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String encrypt(String message){
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] res = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(res);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    public String decrypt(String encrypted){
        try {
            this.cipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] res = this.cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(res);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
