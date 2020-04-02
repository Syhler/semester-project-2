package org.example.domain.password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing
{
    /**
     * encrypt the given string to sha256. The string is giving salt before encrypted.
     * @param password password to hash
     * @return encrypted password
     * @throws Exception if the messageDigest is null
     */
    public static String sha256(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        if (digest == null) throw new Exception();

        password = addSaltToPassword(password);


        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    /**
     * Converts hashed byte to readable hex string. Has been taken from https://www.baeldung.com/sha-256-hashing-java
     * @param hash array of bytes
     * @return a hex string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    //TODO(add salt)
    private static String addSaltToPassword(String password)
    {
        return password;
    }

}
