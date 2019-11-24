import com.medical.client.ClientFunctions;

public class Main {

    private static void client(){
        ClientFunctions clientFunctions = new ClientFunctions();
        if(clientFunctions.createAccount())
            System.out.println("account created");
        //clientFunctions.appendData();   //86-81-71-33-709844-383785-9277917493-3928-120174393-46-762224-7114-8568-63104
        //clientFunctions.getAllData();
    }
    private static void server(){

    }
    public static  void main(String[] args){
        System.out.println("WELCOME TO MEDIC");
        client();
    }
}
