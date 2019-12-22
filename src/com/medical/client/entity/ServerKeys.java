package com.medical.client.entity;

import java.math.BigInteger;

public class ServerKeys {
    private BigInteger publicKeyModules;
    private BigInteger publicKeyExpo;

    public BigInteger getPublicKeyModules() {
        return publicKeyModules;
    }

    public void setPublicKeyModules(BigInteger publicKeyModules) {
        this.publicKeyModules = publicKeyModules;
    }

    public BigInteger getPublicKeyExpo() {
        return publicKeyExpo;
    }

    public void setPublicKeyExpo(BigInteger publicKeyExpo) {
        this.publicKeyExpo = publicKeyExpo;
    }
}
