
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Server class
class Server {

	static ArrayList<ClientHandler> clientThreads = new ArrayList<>();
	static ClientHandler adminPort;
    static HashMap<String, ClientHandler> clients = new HashMap<>();

	public static void main(String[] args) throws IOException {
		
		Socket admin;
		ServerSocket server = new ServerSocket(1234);
		Socket client;
		int counter = 1;
		
		// connect the admin
		while (true) {
			admin = server.accept();
			System.out.println("Admin Connected");
			
			DataOutputStream output = new DataOutputStream(admin.getOutputStream());
			DataInputStream input = new DataInputStream(admin.getInputStream());

			ClientHandler adminSock = new ClientHandler(admin, "admin", output, input);
			Thread t = new Thread(adminSock);
			adminPort = adminSock;
			t.start();
			break;
			
				
		}
		
		// accepts clients 
		while (true) {

			client = server.accept();
			
			System.out.println("New client: " + client.getInetAddress());

			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			DataInputStream input = new DataInputStream(client.getInputStream());
			String name = input.readUTF();
			
			System.out.println(name);

			ClientHandler clientSock = new ClientHandler(client, name, output, input);
			System.out.println(clientSock.userName);
	         System.out.println(clientSock);

			clients.put( clientSock.userName, clientSock );
			
			Thread t = new Thread(clientSock);
			clientThreads.add(clientSock);
			Admin.scores.put(name,0);
			t.start();
			
		}

	}

}
