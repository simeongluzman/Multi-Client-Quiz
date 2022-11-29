
import java.io.DataInputStream ;
import java.io.DataOutputStream ;
import java.io.IOException ;

import java.net.Socket ;
import java.util.Scanner ;

public class ClientHandler implements Runnable
    {

    String userName ;
    Socket clientSocket ;
    DataOutputStream output ;
    DataInputStream input ;
    // Scanner keyBoard = new Scanner(System.in);

    // Constructor
    public ClientHandler( Socket socket,
                          String userName,
                          DataOutputStream output,
                          DataInputStream input )
        {

        this.clientSocket = socket ;
        this.userName = userName ;
        this.output = output ;
        this.input = input ;

        }


    public void run()
        {

        String inputLine ;

        while ( true )
            {
            try
                {
                inputLine = this.input.readUTF() ;
                System.out.println( inputLine ) ;

                // add exit condition

                if ( inputLine == "-exit" )
                    {
                    this.clientSocket.close() ;
                    break ;
                    }

                // add logic for admin ---> All
                // and All only to Admin

                if ( inputLine.charAt( 0 ) == '+' )
                    {

                    for ( ClientHandler client : Server.clientThreads )

                        client.output.writeUTF( inputLine.substring( 1 ) ) ;

                    }
                else if ( inputLine.charAt( 0 ) == '|' )
                {

                for ( ClientHandler client : Server.clientThreads )

                    client.output.writeUTF(inputLine) ;

                }

                else if ( inputLine.charAt( 0 ) == '-' )
                    {
                    Server.adminPort.output.writeUTF( inputLine.substring( 1 ) ) ;
                    }
                else
                    {

                    String[] splitUserInput = inputLine.split( " " ) ;
                    // first word in inputLine is always the username if it's only
                    // for one specific client
                    String username = splitUserInput[ 0 ] ;
                    // get rid of the username
                    inputLine = inputLine.replace( username, "" ).substring( 1 ) ;

                    // find the correct ClientHandler using trhe hashmap and send the
                    // inputLine to the specified client
                    Server.clients.get( username ).output.writeUTF( inputLine ) ;
                    }

                }

            catch ( IOException e )
                {
                e.printStackTrace() ;

                }

            }

        try
            {
            this.input.close() ;
            this.output.close() ;

            }
        catch ( IOException e )
            {
            e.printStackTrace() ;
            }

        }

    // scores.put(input.readLine(), 0);
    // System.out.println(scores);
    /*
     * private void writeToAll(String out) { for (ClientHandler t : clientThreads) {
     * output.println(out); } }
     */

    }
