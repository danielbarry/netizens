package netizens.bank.server;

import java.io.IOException;
import java.net.Socket;

/**
 * Client.java
 *
 * The client as accepted by the sever is handled on an independent thread and
 * processed before replying.
 **/
public class Client extends Thread{
  private Socket socket;

  /**
   * Client()
   *
   * Accepts the client connection only.
   *
   * @param connection The socket connection from the server accept.
   **/
  public Client(Socket connection){
    /* Save the socket connection */
    socket = connection;
  }

  /**
   * run()
   *
   * The run method for the Thread.
   **/
  @Override
  public void run(){
    /* TODO: Write this code. */
    /* Finally, close the socket */
    try{
      /* Close the socket connection */
      socket.close();
    }catch(IOException e){
      /* TODO: Handle case that the socket could not be closed. */
    }
  }
}
