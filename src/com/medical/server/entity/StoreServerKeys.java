package com.medical.server.entity;

import java.math.BigInteger;

public class StoreServerKeys {
    static private BigInteger publicKeyModules;
    static private BigInteger publicKeyExpo;
    static private BigInteger privateKeyModules;
    static private BigInteger privateKeyExpo;

    public static BigInteger getPublicKeyModules() {
        return publicKeyModules;
    }

    public static void setPublicKeyModules(BigInteger publicKeyModules) {
        StoreServerKeys.publicKeyModules = publicKeyModules;
    }

    public static BigInteger getPublicKeyExpo() {
        return publicKeyExpo;
    }

    public static void setPublicKeyExpo(BigInteger publicKeyExpo) {
        StoreServerKeys.publicKeyExpo = publicKeyExpo;
    }

    public static BigInteger getPrivateKeyModules() {
        return privateKeyModules;
    }

    public static void setPrivateKeyModules(BigInteger privateKeyModules) {
        StoreServerKeys.privateKeyModules = privateKeyModules;
    }

    public static BigInteger getPrivateKeyExpo() {
        return privateKeyExpo;
    }

    public static void setPrivateKeyExpo(BigInteger privateKeyExpo) {
        StoreServerKeys.privateKeyExpo = privateKeyExpo;
    }
}
