package com.neonsn0w;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

    private final Logger    logger = LogManager.getLogger(RSA.class);

    private int             bitLength;
    private BigInteger      moduloN;
    private BigInteger      euleroN;
    private BigInteger      esponentePubblicoE;
    private BigInteger      esponentePrivatoD;
    private BigInteger[]    chiavePubblica;
    private BigInteger[]    chiavePrivata;

    public RSA () {
        bitLength = 4096;
        generaChiavi();
    }

    public RSA (int bitLength) {
        this.bitLength = bitLength;
        generaChiavi();
    }

    private void generaChiavi() {
        logger.info("Generating keypairs...");

        BigInteger p = randomPrime();
        logger.debug("First random prime number generated!");
        BigInteger q = randomPrime();
        logger.debug("First random prime number generated!");

        moduloN = p.multiply(q);
        euleroN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        esponentePubblicoE = BigInteger.valueOf(65537);
        chiavePubblica = new BigInteger[]{moduloN, esponentePubblicoE};
        logger.debug("Public keypair generated! (" + moduloN + ", " + esponentePubblicoE + ")");

        esponentePrivatoD = esponentePubblicoE.modInverse(euleroN);
        chiavePrivata = new BigInteger[]{moduloN, esponentePrivatoD};
        logger.debug("Private keypair generated! (" + moduloN + ", " + esponentePrivatoD + ")");
    }

    private BigInteger randomPrime() {
        SecureRandom random = new SecureRandom();
        return BigInteger.probablePrime(bitLength, random);
    }

    public String encrypt(String message) {
        logger.info("Encrypting message...");

        StringBuilder sb = new StringBuilder();

        for (int i=0; i<message.length(); i++) {
            logger.debug("Encrypting character " + (i+1) + " out of " + message.length());
            BigInteger enc = BigInteger.valueOf((int) message.charAt(i)).modPow(esponentePubblicoE, moduloN);
            sb.append(enc).append(" ");
        }

        return sb.toString();
    }

    public String decrypt(String message) {
        logger.info("Decrypting message...");
        StringBuilder decsb = new StringBuilder();
        String[] nums = message.split(" ");

        for (int i=0; i<nums.length; i++) {
            logger.debug("Decrypting character " + (i+1) + " out of " + nums.length);
            BigInteger encrypted = new BigInteger(nums[i]);
            BigInteger decrypted = encrypted.modPow(esponentePrivatoD, moduloN);
            decsb.append((char) decrypted.intValue());
        }

        return decsb.toString();
    }

    public int getBitLength() {
        return bitLength;
    }

    public BigInteger getModuloN() {
        return moduloN;
    }

    public BigInteger getEuleroN() {
        return euleroN;
    }

    public BigInteger getEsponentePubblicoE() {
        return esponentePubblicoE;
    }

    public BigInteger getEsponentePrivatoD() {
        return esponentePrivatoD;
    }

    public BigInteger[] getChiavePubblica() {
        return chiavePubblica;
    }

    public BigInteger[] getChiavePrivata() {
        return chiavePrivata;
    }
}
