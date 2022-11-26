
import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {

	// driver code

	
	final static int port = 1234;

	public static void main(String[] args) throws IOException {
		

		System.out.println("Please Enter your Username without Spaces");
		
		Scanner kB = new Scanner(System.in);
		
		String uName = kB.nextLine();
		
		
		InetAddress ip = InetAddress.getByName("localhost");
        
        // establish the connection
        Socket serverSock = new Socket(ip, port);
        
    	DataOutputStream output = new DataOutputStream(serverSock.getOutputStream());
		DataInputStream input = new DataInputStream(serverSock.getInputStream());
		
		output.writeUTF(uName);
		
		 Thread send = new Thread(new Runnable() 
	        {
	            @Override
	            public void run() {
	                while (true) {
	  
	                    // read the message to deliver.
	                    String userInput = kB.nextLine();
	                      
	                    try {
	                        
	                        output.writeUTF('-' + userInput);
	                        
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        });
		 
		 Thread recieve = new Thread(new Runnable() 
	        {
	            @Override
	            public void run() {
	  
	                while (true) {
	                    try {
	                        // read the message sent to this client
	                        String userInput = input.readUTF();
	                        System.out.println(userInput);
	                    } catch (IOException e) {
	  
	                        e.printStackTrace();
	                    }
	                }
	            }
	        });
		 
		 send.start();
		 recieve.start();
	}
}
