package me.galaxyeater.pfserver.diagnosis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();
            md.update(text.getBytes());

            byte[] bytes = md.digest();
            for (byte b : bytes) {
                sb.append(Integer.toHexString(b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
