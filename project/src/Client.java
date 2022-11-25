

import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {
	
	// driver code
	public static void main(String[] args)
	{
		// establish a connection by providing host and port
		// number 2
		
		
		System.out.println("Please Enter your Username without Spaces");
		Scanner nameScanner = new Scanner(System.in);
		String uName = nameScanner.nextLine();
		//nameScanner.close();
		
		
		
		
		try (Socket socket = new Socket("localhost", 1234)) {
			
			// writing to server
			PrintWriter out = new PrintWriter(
				socket.getOutputStream(), true);

			// reading from server
			BufferedReader in
				= new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// object of scanner class
			BufferedReader kB
			= new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			
			out.println(uName);

			while (true) {
				
				System.out.println("Server wrote " + in.readLine());
				// reading from user
				line = kB.readLine();
				if (line == "q")
					
					break;

			
				
				out.println(line);
				
			}
			
			// closing the scanner object
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
