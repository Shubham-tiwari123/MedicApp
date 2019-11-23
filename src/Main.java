import com.medical.client.ClientFunctions;

public class Main {

    private static void client(){
        ClientFunctions clientFunctions = new ClientFunctions();
        if(clientFunctions.createAccount())
            System.out.println("account created");
        clientFunctions.getAllData();
    }
    private static void server(){

    }
    public static  void main(String[] args){
        System.out.println("WELCOME TO MEDIC");
        client();
    }
}
