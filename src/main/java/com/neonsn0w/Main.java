package com.neonsn0w;

import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.WARN);

        System.out.print("Enter the message to encrypt: ");
        String s = sc.nextLine();
        RSA rsa = new RSA();
        System.out.println("BEGINNING OF ENCRYPTED MESSSAGE");
        s = rsa.encrypt(s);
        System.out.println(s);
        System.out.println("END OF ENCRYPTED MESSSAGE");
        System.out.println("Decrypted message: " + rsa.decrypt(s));
        System.out.println("Public key: (" + rsa.getN() + ", " + rsa.getPublicExponentE() + ")");
        System.out.println("Private key: (" + rsa.getN() + ", " + rsa.getPrivateExponentD() + ")");
    }
}
