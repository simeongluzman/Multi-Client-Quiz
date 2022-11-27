
import java.io.* ;
import java.net.* ;
import java.util.* ;

// Client class

/**
 * 5 questions check answer keep score print username everytime print out score at
 * the end allow client to type only 1, 2, 3, 4 end game + final scores send the
 * client file to others
 *
 * @author Simeon Gluzman
 * @version 1.0.0 2022-11-27 Initial implementation
 */
class Admin
    {

    // driver code

    public static boolean flag = false ;
    public static boolean typedNext = false ;
    final static int port = 1234 ;
    static ArrayList<String> userAnswers = new ArrayList<>() ;
    static ArrayList<String> questions = new ArrayList<>() ;
    static String question1 = "What is the capital of France?\n (1) Paris\n (2) Monteppilier \n (3) Hong Kong\n (4) Nice\n" ;
    static String question2 = "What is the height of tajmahal (in meters)?\n (1) 1 \n (2) 24\n (3) 73\n (4) 274\n" ;
    static String question3 = "Which actor/actoress from Friends went to WIT? \n (1) Jennifer Anniston\n (2) Matthew Perry\n (3) David Schwimmer\n (4) Matt Leblanc\n" ;
    static String question4 = "What's the smallest country in the world?\n (1) North Korea\n (2) Monaco\n (3) Vatican City\n (4) Tuvalu\n" ;
    static String question5 = "What does “www” stand for in a website browser??\n (1) Web Web Web\n (2) World Wide Web\n (3) Win Win Win\n (4) Wide World Web\n" ;

    static String adminInput = "" ;
    static HashMap<String, Integer> scores = new HashMap<>() ;
    static HashMap<String, String> questionsAndAnswers = new HashMap<>() ;
    static int currentIndex = 0 ;
    static int currentScore = 0 ;
    static String currentUserName = "" ;

    public static void main( String[] args ) throws IOException
        {

        questionsAndAnswers.put( question1, "1" ) ;
        questions.add( question1 ) ;
        questionsAndAnswers.put( question2, "3" ) ;
        questions.add( question2 ) ;
        questionsAndAnswers.put( question3, "4" ) ;
        questions.add( question3 ) ;
        questionsAndAnswers.put( question4, "3" ) ;
        questions.add( question4 ) ;
        questionsAndAnswers.put( question5, "2" ) ;
        questions.add( question5 ) ;

        System.out.println( "Connecting to Server ..." ) ;

        Scanner kB = new Scanner( System.in ) ;

        InetAddress ip = InetAddress.getByName( "localhost" ) ;

        // establish the connection
        Socket serverSock = new Socket( ip, port ) ;

        DataOutputStream output = new DataOutputStream( serverSock.getOutputStream() ) ;
        DataInputStream input = new DataInputStream( serverSock.getInputStream() ) ;

        Thread recieve = new Thread( new Runnable()
            {

            @Override
            public void run()
                {

                while ( true )
                    {
                    try
                        {
                        if ( currentIndex >= questions.size() )
                            {
                            break ;
                            }
                        // read the message sent to this client
                        String userInput = input.readUTF() ;
                        System.out.println(userInput);

                        String currentQuestion = questions.get( currentIndex++ ) ;
                        String correctAnswer = questionsAndAnswers.get( currentQuestion ) ;
                        
                        String[] splitUserInput = userInput.split(" ");
                        String username = splitUserInput[0];
                        currentUserName = username;
                        userInput = splitUserInput[1];
                        


                        // get rid of the username
                        System.out.println(username);

                        // make sure the client types 1, 2, 3, or 4
                        while ( !userInput.equals( "1" ) &&
                                !userInput.equals( "2" ) &&
                                !userInput.equals( "3" ) &&
                                !userInput.equals( "4" ) )
                            {
                            output.writeUTF( username + " " + "Please enter 1, 2, 3, or 4: " ) ;
                            userInput = input.readUTF() ;
                            splitUserInput = userInput.split(" ");
                            username = splitUserInput[0];
                            userInput = splitUserInput[1];
                            System.out.println(username);

                            }

                        if ( userInput.equals( correctAnswer ) )
                            {
                            scores.put(username, scores.getOrDefault( username, 0 ) + 1);
                            }

                        System.out.println( userInput ) ;

                        if ( flag )
                            {
                            userAnswers.add( userInput ) ;
                            System.out.println( userAnswers ) ;
                            }

                        }
                    catch ( IOException e )
                        {

                        e.printStackTrace() ;
                        }
                    }
                }
            } ) ;

        Thread send = new Thread( new Runnable()
            {

            @Override
            public void run()
                {
                while ( true )
                    {

                    // read the message to deliver.
                    String userInput = kB.nextLine() ;

                    if ( userInput.equals( "start" ) )
                        {
                        flag = true ;

                        for ( int i = 0 ; i < questions.size() ; i++ )
                            {
                            typedNext = false ;
                            String question = questions.get( i ) ;
                            try
                                {
                                output.writeUTF( '+' + question ) ;

                                while ( !typedNext )
                                    {
                                    if ( kB.nextLine().equals( "next" ) )
                                        {
                                        typedNext = true ;
                                        }
                                    }
                                output.writeUTF( '+' + "Answer: " +
                                                 questionsAndAnswers.get( question ) ) ;

                                }
                            catch ( IOException e1 )
                                {
                                // TODO Auto-generated catch block
                                e1.printStackTrace() ;
                                }
                            }

                        continue ;

                        }

                    try
                        {
                        output.writeUTF( currentUserName + " " + "Your score: " +
                                         scores.get( currentUserName ) + " / " +
                                         questionsAndAnswers.size() ) ;

                        }
                    catch ( IOException e )
                        {
                        e.printStackTrace() ;
                        }
                    }
                }
            } ) ;

        send.start() ;
        recieve.start() ;
        }


    public synchronized static void startGame( DataOutputStream out,
                                               DataInputStream in,
                                               Scanner scn )
        throws IOException
        {

        out.writeUTF( '+' + question1 ) ;
        long t = System.currentTimeMillis() ;
        long end = t + 15000 ;
        while ( System.currentTimeMillis() < end )
            {
            System.out.println( in.readUTF() ) ;

            }

        System.out.println( "Times up!" ) ;

        }

    }
