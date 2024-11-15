package com.neonsn0w;

public class Main {
    public static void main(String[] args) {
        RSA rsa = new RSA();
        String s = rsa.encrypt("The quick brown fox jumps over the lazy dog");
        System.out.println(s);
        System.out.println(rsa.decrypt(s));
    }
}
