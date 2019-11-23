package com.medical.client;

import java.util.Scanner;

public interface ClientMethods {
    public Scanner SCANNER = new Scanner(System.in);

    public boolean createAccount();
    public boolean appendData();
    public void getAllData();

}
