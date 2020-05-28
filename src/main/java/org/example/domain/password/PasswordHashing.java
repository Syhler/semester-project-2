package org.example.domain.password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordHashing
{
    private static final Random random = new SecureRandom();
    private static final String PEPPER = "1BQBKgmisvYIZMJ1tdPn";

    /**
     * encrypt the given string to sha256. The string is giving salt before encrypted.
     * @param password password to hash
     * @return encrypted password
     * @throws Exception if the messageDigest is null
     */
    public static String sha256(String password, String salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        if (digest == null) throw new Exception();

        password = hashPassword(password, salt);
        password = hashPassword(password, PEPPER);

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

    /**
     * adds salt to the password in the middle of the given password
     * @return salted password
     */
    private static String hashPassword(String password, String salt)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(password);
        int middleIndex = password.length() / 2;
        builder.insert(middleIndex, salt);

        return builder.toString();
    }


    /**
     * generates a 16 bytes salt
     * @return return the generated salt
     */
    public static String generateSalt()
    {
        var bytes = new byte[16];
        random.nextBytes(bytes);

        return bytesToHex(bytes);
    }
}
