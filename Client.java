import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

public class Client
{
    private static final String HOST = "localhost";
    private static final int PORT = 61174;
    OutputStream out;   
    BufferedReader in;
    BufferedReader userInput;


    public static void main(String [] args)
    {
        new Client();
    }

    public Client()
    {
        String input;
        String received;
        Socket client= null;    
        String exitMessage="Exit";    
        try
        {
            byte[] buffer=new byte[1024];
            userInput=new BufferedReader(new InputStreamReader(System.in));

                client = new Socket(HOST, PORT); // Creates a socket connection
                out = client.getOutputStream();
                in= new BufferedReader(new InputStreamReader(client.getInputStream()));
                
                received=in.readLine();
                System.out.println("Received from Server : "+received);

                while(true){

                    System.out.print("\nEnter message to respond:");
                    input=userInput.readLine(); //blocked for keyboard input
                    input=input.concat("\n");
                    buffer=input.getBytes();
                    out.write(buffer,0,buffer.length);
                    out.flush();
                    System.out.println("\n**** Waiting for Server.. ****\n\n");
                    received=in.readLine(); //blocked for server's input
                    System.out.println("Received message :"+received);
                    if(input.equals(exitMessage)){
                        System.out.println("Exiting...");
                        break;
                    }

                }
                in.close();
                out.close();
                client.close();
                 
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
