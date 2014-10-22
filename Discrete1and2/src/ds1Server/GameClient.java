package ds1Server;

 import java.io.IOException;
 import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.util.Scanner;
 import java.util.concurrent.Semaphore;

 public class GameClient {
     public static final int NEW_GAME           = 1;
     public static final int HERE_IS_MY_MOVE    = 2;
     public static final int I_WIN              = 3;
     public static final int HERE_IS_A_MESSAGE  = 4;
     public static final int YOU_ARE_PLAYER_1   = 5;
     public static final int YOU_ARE_PLAYER_2   = 6;
     public static final int SHUTTING_DOWN      = 7;
     public static final int YOU_CAN_SHUT_DOWN  = 8;
     public static final int DRAW               = 9;
     public static final int EARLY_TERMINATION  = 10;
     public static final int PLAYER_2_JOINED    = 11;
     
     private  ClientSocketThread clientSocketThread;
     public   int gameID;
     public   String otherPlayerMove;
     private  Semaphore moveSemaphore;
     private  Semaphore gameSetupSemaphore;
     public   String myName;
     private   String otherPlayerName;
     public   int    playerNumber;
     
     public GameClient(int gid){
         clientSocketThread = null;
         otherPlayerMove = "";
         moveSemaphore      = new Semaphore(0);
         gameSetupSemaphore = new Semaphore(0);
         gameID = gid;
     }
     
     public void connectToServer(){
         try {
             System.out.print("Connecting to server . . .");
             //System.out.println("GC: About to create the CST");
             byte[] x = {-72,128>>7,-65,(1<<3)+1};
             clientSocketThread = new ClientSocketThread(InetAddress.getByAddress(x));
             //clientSocketThread = new ClientSocketThread(InetAddress.getLocalHost());
             clientSocketThread.start();
             //System.out.println("GC: Done calling the run method");
         } catch (UnknownHostException e) {
             System.err.println("Could not connect to server");
             e.printStackTrace();
         }
     }
     
     
     public int startGame(String name){
         myName = name;
         clientSocketThread.startGame(gameID, name);
         try {
             gameSetupSemaphore.acquire(); // We get this when the server tells us our player #
         } catch (InterruptedException e) {
             System.err.println("Could not acquire gameSetupSemaphore");
             e.printStackTrace();
         } 
         return playerNumber;
     }
     
     
     public void sendMove(String move){
         clientSocketThread.sendMove(move);
     }
     
     
     public String getOtherPlayerName() {
         return otherPlayerName;
     }

     public String getMove(){
         try {
             moveSemaphore.acquire();
         } catch (InterruptedException e) {
             System.err.println("GS: Could not acquire semaphore permit");
             e.printStackTrace();
         }
         return otherPlayerMove;
     }
     
     
     public void declareWin(){
         clientSocketThread.declareWin();
     }
     
     
     public void declareDraw(){
         clientSocketThread.declareDraw();
     }
     
     
     public void shutdown(){
         //System.out.println("GC: About to shutdown the CST");
         clientSocketThread = null;
     }

     
     /**
      * @author hochberg
      * @Purpose:
      * @v0.1 -Feb 2, 2014
      */
     public  class ClientSocketThread extends Thread {
         public ObjectOutputStream out;
         public ObjectInputStream in;
         Socket socket;
         boolean alive;
         
         public ClientSocketThread(InetAddress iadd){
             in = null;
             out = null;
             alive = true;
             
             // Try to create a socket to that server
             try {
                 socket = new Socket(iadd, 17351);
             } catch (IOException e) {
                 System.err.println("Failed to connect to server.");
                 e.printStackTrace();
             }
      
             // Create a writer
             try {
                 out = new ObjectOutputStream(socket.getOutputStream());
                 out.flush();
             } catch (IOException e) {
                 System.err.println("Could not send info to the client " + socket.getInetAddress().getHostName());
                 return;
             }
      
             // Create a reader
             try {
                 in = new ObjectInputStream(socket.getInputStream());
             } catch (IOException e) {
                 System.err.println("Could not read info from client " + socket.getInetAddress().getHostName());
                 return;
             }
             //System.out.println("CST: new CST created.");     
         }
      
         public void run(){
             System.out.println("Connected.");
      
             while(alive){
                 int message = -1;
                 try {
                     //System.out.println("CST: Awaiting int from server");
                     message = in.readInt();
                     //System.out.println("CST: Just received " + message + " from server");
                 } catch (IOException e) {
                     System.err.println("ClientSocketThread: error reading from socket");
                     e.printStackTrace();
                     return; // Terminate the thread
                 }
      
                 if(message == HERE_IS_A_MESSAGE){
                     String m = "";
                     try {
                         m = (String)in.readObject();
                     } catch (IOException e) {
                         System.err.println("ClientSocketThread:  Failed to read object");
                     } catch (ClassNotFoundException e) {
                         System.err.println("ClientSocketThread:  Couldn't recognize the Player class");
                         e.printStackTrace();
                     }
                     System.out.println("Incoming Message: " + m);
                 }
      
                 if(message == HERE_IS_MY_MOVE){
                     try {
                         otherPlayerMove = (String)in.readObject();
                     } catch (IOException e) {
                         System.err.println("ClientSocketThread:  Failed to read object");
                     } catch (ClassNotFoundException e) {
                         System.err.println("ClientSocketThread:  Couldn't recognize the Player class");
                         e.printStackTrace();
                     }
                     moveSemaphore.release();
                 }
      
                 if(message == YOU_ARE_PLAYER_1){
                     System.out.println("You are Player 1. Waiting for opponent.");
                     playerNumber = 1;
                     try {
                         otherPlayerName = (String)in.readObject();
                     } catch (IOException e) {
                         System.err.println("ClientSocketThread:  Failed to read other player's name");
                     } catch (ClassNotFoundException e) {
                         System.err.println("ClientSocketThread:  Couldn't recognize the String class");
                         e.printStackTrace();
                     }
                 }
      
                 if(message == PLAYER_2_JOINED){
                     try {
                         otherPlayerName = (String)in.readObject();
                     } catch (IOException e) {
                         System.err.println("ClientSocketThread:  Failed to read other player's name");
                     } catch (ClassNotFoundException e) {
                         System.err.println("ClientSocketThread:  Couldn't recognize the String class");
                         e.printStackTrace();
                     }
                     System.out.println(otherPlayerName + " has joined as Player 2.");
                     gameSetupSemaphore.release();
                 }

                 if(message == YOU_ARE_PLAYER_2){
                     try {
                         otherPlayerName = (String)in.readObject();
                     } catch (IOException e) {
                         System.err.println("ClientSocketThread:  Failed to read other player's name");
                     } catch (ClassNotFoundException e) {
                         System.err.println("ClientSocketThread:  Couldn't recognize the String class");
                         e.printStackTrace();
                     }
                     System.out.println("You are Player 2, playing against " + otherPlayerName);
                     playerNumber = 2;
                     gameSetupSemaphore.release();
                 }
                 
                 if(message == I_WIN){
                     System.out.println("Game over. We lose.");
                     alive = false;
                     try {
                         out.writeInt(SHUTTING_DOWN);
                         out.flush();
                     } catch (IOException e) {
                         System.err.println("CST: Failed to send shutdown message");
                         e.printStackTrace();
                     }
                 }
                 
                 if(message == DRAW){
                     System.out.println("Game over. It's a tie.");
                     alive = false;
                     try {
                         out.writeInt(SHUTTING_DOWN);
                         out.flush();
                     } catch (IOException e) {
                         System.err.println("CST: Failed to send shutdown message");
                         e.printStackTrace();
                     }
                 }
                 
                 if(message == YOU_CAN_SHUT_DOWN){
                     //System.out.println("I can shut down");
                     alive = false;
                     try {
                         out.writeInt(SHUTTING_DOWN);
                         out.flush();
                     } catch (IOException e) {
                         System.err.println("CST: Failed to send shutdown message");
                         e.printStackTrace();
                     }
                 }
                 
                 if(message == EARLY_TERMINATION){
                     System.out.println("Early Termination. Game over.");
                     alive = false;
                     try {
                         out.writeInt(SHUTTING_DOWN);
                         out.flush();
                     } catch (IOException e) {
                         System.err.println("CST: Failed to send shutdown message");
                         e.printStackTrace();
                     }
                 }
             }
             //System.out.println("Loop ended.");
         }


         public void startGame(int gameID, String name){
             try {
                 out.writeInt(NEW_GAME);
                 out.writeInt(gameID);
                 out.writeObject(name);
                 out.flush();
             } catch (IOException e) {
                 System.err.println("CST: Failed to start new game");
                 e.printStackTrace();
             }
         }


         public void sendMove(String move){
             try {
                 out.writeInt(HERE_IS_MY_MOVE);
                 out.writeObject(move);
                 out.flush();
             } catch (IOException e) {
                 System.err.println("CST: Failed to send move");
                 e.printStackTrace();
             }
         }
      
         public void declareWin(){
             try {
                 out.writeInt(I_WIN);
                 out.flush();
                 //System.out.println("Declared victory. Shutting down");
             } catch (IOException e) {
                 System.err.println("ClientSocketThread:  Failed to exit the server");
                 e.printStackTrace();
             }
         }
      
         public void declareDraw(){
             try {
                 out.writeInt(DRAW);
                 out.flush();
                 //System.out.println("Declared victory. Shutting down");
             } catch (IOException e) {
                 System.err.println("ClientSocketThread:  Failed to exit the server");
                 e.printStackTrace();
             }
         }
     }
 }