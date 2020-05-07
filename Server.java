import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;

public class Server
{
    OutputStream out;
    BufferedReader in;
    BufferedReader userInput;
    public static void main(String [] args)
    {
        new Server();
    }

    public Server()
    {  
        System.out.println("Server running");
        ServerSocket serverSocket = null;
        Socket connection;
        byte[] buffer=new byte[1024];
        String sendMessage;
        String receiveMessage;
        String exitMessage="Exit";
        try {
            serverSocket = new ServerSocket(61174); // Binds to the server port

            while(true)
            {
                connection=serverSocket.accept();
                userInput=new BufferedReader(new InputStreamReader(System.in));
                out=connection.getOutputStream();
                in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sendMessage="Welcome, please send your message\n";
                buffer=sendMessage.getBytes();
                out.write(buffer,0,buffer.length);
                out.flush();
                //System.out.println("Sent welcome");
                while(true)
                {
                    System.out.println("\n**** Waiting for Client.. ****\n\n");
                    receiveMessage=in.readLine(); //blocked for input
                    System.out.println("\nReceived Message : "+receiveMessage);
                    if(receiveMessage.equals(exitMessage)) {
                        System.out.println("Client exiting...");
                        sendMessage="Thank you! Goodbye\n";
                        out.write(sendMessage.getBytes());
                        out.flush();
                        break;
                    }
                    else
                    {
                        System.out.print("\nEnter Message to respond:");
                        sendMessage=userInput.readLine();
                        sendMessage=sendMessage.concat("\n");
                        out.write(sendMessage.getBytes());
                        out.flush();
                    }

                }
                in.close();
                out.close();
                connection.close();
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                serverSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
