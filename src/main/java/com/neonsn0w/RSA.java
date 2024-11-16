package com.neonsn0w;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * A class that generates an RSA keypair when created and offers methods for encrypting and decrypting messages.
 */
public class RSA {

    private final Logger    logger = LogManager.getLogger(RSA.class);

    private int             bitLength;
    private BigInteger      p;
    private BigInteger      q;
    private BigInteger      n;
    private BigInteger      eulerN;
    private BigInteger      publicExponentE;
    private BigInteger      privateExponentD;
    private BigInteger[]    publicKey;
    private BigInteger[]    privateKey;

    public RSA () {
        bitLength = 4096;
        generaChiavi();
    }

    public RSA (int bitLength) {
        this.bitLength = bitLength;
        generaChiavi();
    }

    private void generaChiavi() {
        logger.info("Generating keypair...");

        p = randomPrime();
        logger.debug("First random prime number generated!");
        q = randomPrime();
        logger.debug("First random prime number generated!");

        n = p.multiply(q);
        eulerN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        publicExponentE = BigInteger.valueOf(65537);
        publicKey = new BigInteger[]{n, publicExponentE};
        logger.debug("Public key generated! (" + n + ", " + publicExponentE + ")");

        privateExponentD = publicExponentE.modInverse(eulerN);
        privateKey = new BigInteger[]{n, privateExponentD};
        logger.debug("Private key generated! (" + n + ", " + privateExponentD + ")");
    }

    private BigInteger randomPrime() {
        SecureRandom random = new SecureRandom();
        return BigInteger.probablePrime(bitLength, random);
    }

    /**
     * Each character is separated and individually converted to int and encrypted.
     * @param message
     * @return Encrypted characters separated by a space
     */
    public String encrypt(String message) {
        logger.info("Encrypting message...");

        StringBuilder sb = new StringBuilder();

        for (int i=0; i<message.length(); i++) {
            logger.debug("Encrypting character " + (i+1) + " out of " + message.length());
            BigInteger enc = BigInteger.valueOf((int) message.charAt(i)).modPow(publicExponentE, n);
            sb.append(enc).append(" ");
        }

        return sb.toString();
    }

    /**
     * It decrypts a string that is in the same format that is given by encrypt().
     * @param message
     */
    public String decrypt(String message) {
        logger.info("Decrypting message...");
        StringBuilder decsb = new StringBuilder();
        String[] nums = message.split(" ");

        for (int i=0; i<nums.length; i++) {
            logger.debug("Decrypting character " + (i+1) + " out of " + nums.length);
            BigInteger encrypted = new BigInteger(nums[i]);
            BigInteger decrypted = encrypted.modPow(privateExponentD, n);
            decsb.append((char) decrypted.intValue());
        }

        return decsb.toString();
    }

    public int getBitLength() {
        return bitLength;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getEulerN() {
        return eulerN;
    }

    public BigInteger getPublicExponentE() {
        return publicExponentE;
    }

    public BigInteger getPrivateExponentD() {
        return privateExponentD;
    }

    public BigInteger[] getPublicKey() {
        return publicKey;
    }

    public BigInteger[] getPrivateKey() {
        return privateKey;
    }
}
