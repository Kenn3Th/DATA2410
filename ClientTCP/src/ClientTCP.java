import java.net.*;
import java.io.*;

public class ClientTCP {

    public static void main(String[] args) throws IOException{
        /* hostName kan settes i cmd når man kjører koden i cmd*/
        String hostName = "127.0.0.1"; //Local host, default

        /* portNumber kan settes i cmd når man kjører koden der i cmd*/
        int portNumber = 5555; //Default port

        if(args.length>0){
            hostName = args[0];
            if(args.length>1){
                portNumber = Integer.parseInt(args[1]);
                if(args.length>2){
                    System.err.println("Usage: java EchoClientTCP [<hostname>] [<port number>]");
                    System.exit(1);
                }
            }
        }
        System.out.println("Hello, I am the client\n....Fucker!");

        try(
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
            String userInput;
            System.out.print("I (Client) [" + InetAddress.getLocalHost() +
                    ":" + clientSocket.getLocalPort() + "] > ");
            while(((userInput = stdIn.readLine()) != null) && !userInput.isEmpty()){
                out.println(userInput);
                String receivedText = stdIn.readLine();
                System.out.println("Server [" + hostName +  ":" + portNumber + "] >" + receivedText);
                System.out.print("I (Client) [" + clientSocket.getLocalAddress().getHostAddress() +
                        ":" + clientSocket.getLocalPort()+ "] > ");
            }
        }catch(UnknownHostException e){
            System.err.println("Unkown host" + hostName);
            System.exit(1);
        }catch(IOException e){
            System.err.println("Couldn't get I/O connection to "+ hostName);
            System.exit(1);
        }
    }
}