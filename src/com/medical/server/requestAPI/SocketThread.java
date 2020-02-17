package com.medical.server.requestAPI;

import com.medical.server.service.ConnectToHospital;

public class SocketThread extends Thread {
    private ConnectToHospital connect = new ConnectToHospital();
    @Override
    public void run(){
        try {
            int count = 0;
            int statusCode = 200;
            String clientData = "";
            String clientKeys="";
            System.out.println("run function");
            if(connect.connectUsingSocket(4000)){
                System.out.println("connected");
                Thread.sleep(2000);
                while (ConnectToHospital.socket.isConnected()){
                    clientData = connect.readClientData();
                    System.out.println("Data from client:" + clientData);
                    switch (count) {
                        case 0:
                            if (connect.verifyNetwork(clientData)) {
                                System.out.println("Network is secured");
                                if (connect.sendData("200"))
                                    count++;
                                else {
                                    statusCode = 400;
                                    connect.closeConnections();
                                }
                            } else {
                                statusCode = 400;
                                connect.sendData("400");
                                connect.closeConnections();
                                System.out.println("Error in network.....Closing socket");
                            }
                            break;

                        case 1:
                            if (connect.verifyClientKeys(clientData)) {
                                clientKeys = clientData;
                                System.out.println("keys verified");
                                String serverKeys = connect.getServerKeys();
                                if (connect.sendData("200")) {
                                    System.out.println("Sending server keys to client");
                                    if (connect.sendData(serverKeys)) {
                                        count++;
                                    } else {
                                        statusCode = 400;
                                        connect.closeConnections();
                                    }
                                } else {
                                    statusCode = 400;
                                    connect.closeConnections();
                                }
                            } else {
                                statusCode = 400;
                                connect.sendData("400");
                                connect.closeConnections();
                                System.out.println("Client keys are tampered...Closing socket");
                            }
                            break;

                        case 2:
                            if (clientData.equals("200")) {
                                //store the data in db and will send a final 200 to client
                                if(connect.storeClientKeys(clientKeys)) {
                                    System.out.println("keys stored");
                                    connect.sendData("200OK");
                                    connect.closeConnections();
                                }
                                else{
                                    System.out.println("Error in db");
                                    connect.sendData("400");
                                    statusCode = 400;
                                    connect.closeConnections();
                                }
                            }else{
                                System.out.println("Server keys are tampered");
                                statusCode = 400;
                                connect.closeConnections();
                            }
                    }
                    if (ConnectToHospital.socket.isClosed()) {
                        System.out.println("socket is closed");
                        ConnectToHospital.server.close();
                        System.out.println(ConnectToHospital.server.getLocalPort()+" "+
                                ConnectToHospital.server.isClosed());
                        break;
                    }
                }
            } else {
                statusCode = 400;
                System.out.println("Something went wrong....try again");
            }

            if (statusCode == 200) {
                System.out.println("Keys exchanged and stored in db");
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("catch socket...closing con");
            try {
                connect.closeConnections();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
