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
        BigInteger p = randomPrime();
        BigInteger q = randomPrime();
        moduloN = p.multiply(q);
        euleroN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        esponentePubblicoE = BigInteger.valueOf(65537);
        esponentePrivatoD = esponentePubblicoE.modInverse(euleroN);
        chiavePubblica = new BigInteger[]{moduloN, esponentePubblicoE};
        chiavePrivata = new BigInteger[]{moduloN, esponentePrivatoD};
    }

    private BigInteger randomPrime() {
        SecureRandom random = new SecureRandom();
        return BigInteger.probablePrime(bitLength, random);
    }

    public String encrypt(String message) {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<message.length(); i++) {
            BigInteger enc = BigInteger.valueOf((int) message.charAt(i)).modPow(esponentePubblicoE, moduloN);
            sb.append(enc).append(" ");
        }

        return sb.toString();
    }

    public String decrypt(String message) {
        StringBuilder decsb = new StringBuilder();
        String[] nums = message.split(" ");

        for (String num : nums) {
            BigInteger encrypted = new BigInteger(num);
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
