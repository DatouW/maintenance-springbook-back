package com.group8.code.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 1000;

    /**
     * Generates a hash for the given input using SHA-256 with salt and multiple iterations.
     *
     * @param input The plain text input.
     * @return The hashed input in Base64 format.
     */
    public static String generateHash(String input) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        byte[] hash = hashInput(input, salt, ITERATIONS);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifies if the input matches the hashed input.
     *
     * @param rawInput The plain text input.
     * @param hashedInput The hashed input in Base64 format.
     * @return true if the inputs match, otherwise false.
     */
    public static boolean verifyHash(String rawInput, String hashedInput) throws NoSuchAlgorithmException {
        String[] parts = hashedInput.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = Base64.getDecoder().decode(parts[1]);

        byte[] rawHash = hashInput(rawInput, salt, ITERATIONS);
        return MessageDigest.isEqual(rawHash, hash);
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hashInput(String input, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        digest.reset();
        digest.update(salt);
        byte[] hashed = digest.digest(input.getBytes());

        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }

        return hashed;
    }
}
