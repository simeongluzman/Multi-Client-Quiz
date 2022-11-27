
import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Admin {

	// driver code
	
	public static boolean flag = false;
	final static int port = 1234;
	static ArrayList<String> answers = new ArrayList<>();
	static ArrayList<String> questions = new ArrayList<>();
	static String question1 = "What is the capital of France? (1)-Paris  (2)-Monteppilier";
	static String question2 = "What is the height of tajmahal?";
	static String adminInput = "";

	public static void main(String[] args) throws IOException {

		questions.add(question1);
		questions.add(question2);
		System.out.println("Connecting to Server ...");

		Scanner kB = new Scanner(System.in);

		InetAddress ip = InetAddress.getByName("localhost");

		// establish the connection
		Socket serverSock = new Socket(ip, port);

		DataOutputStream output = new DataOutputStream(serverSock.getOutputStream());
		DataInputStream input = new DataInputStream(serverSock.getInputStream());

		Thread recieve = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					try {
						// read the message sent to this client
						String userInput = input.readUTF();
						System.out.println(userInput);
						if (flag) {
							answers.add(userInput);
							System.out.println(answers);
						}
							

					} catch (IOException e) {

						e.printStackTrace();
					}
				}
			}
		});

		Thread send = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					// read the message to deliver.
					String userInput = kB.nextLine();

					if (userInput.equals("start")) {
						flag = true;

						for (String q : questions) {
							try {
								output.writeUTF('+' + q);

								while (!kB.hasNext()) {

									

								}

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						continue;

					}
					
					

					try {

						output.writeUTF('+' + userInput);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		send.start();
		recieve.start();

	}

	public synchronized static void startGame(DataOutputStream out, DataInputStream in, Scanner scn)
			throws IOException {

		out.writeUTF('+' + question1);
		long t = System.currentTimeMillis();
		long end = t + 15000;
		while (System.currentTimeMillis() < end) {
			System.out.println(in.readUTF());

		}

		System.out.println("Times up!");

	}
}
