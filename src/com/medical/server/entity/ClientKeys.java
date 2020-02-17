package com.medical.server.entity;

import java.math.BigInteger;

public class ClientKeys {

    private BigInteger clientPubKeyMod ;
    private BigInteger clientPubKeyExpo ;

    public BigInteger getClientPubKeyMod() {
        return clientPubKeyMod;
    }

    public void setClientPubKeyMod(BigInteger clientPubKeyMod) {
        this.clientPubKeyMod = clientPubKeyMod;
    }

    public BigInteger getClientPubKeyExpo() {
        return clientPubKeyExpo;
    }

    public void setClientPubKeyExpo(BigInteger clientPubKeyExpo) {
        this.clientPubKeyExpo = clientPubKeyExpo;
    }
}
