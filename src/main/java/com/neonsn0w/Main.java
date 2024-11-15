package com.neonsn0w;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
    public static void main(String[] args) {
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);

        RSA rsa = new RSA();
        String s = rsa.encrypt("The quick brown fox jumps over the lazy dog");
        System.out.println(s);
        System.out.println(rsa.decrypt(s));
    }
}
