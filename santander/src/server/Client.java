package netizens.bank.server;

import java.io.InputStream;
import java.io.IOException;
import java.net.Socket;
import netizens.bank.utils.Error;

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
    /* Read the maximum size */
    byte[] request = new byte[readSize];
    /* Safely request bytes from stream */
    try{
      /* Get JSON request*/
      InputStream is = socket.getInputStream();
      is.read(request, 0, readSize);
    }catch(IOException e){
      /* Default error handling */
      Error.safeThrow(e, false);
    }
    /* Cast to String */
    String raw = new String(request);
    /* Parse the object */
    /* Finally, close the socket */
    try{
      /* Close the socket connection */
      socket.close();
    }catch(IOException e){
      /* Default error handling */
      Error.safeThrow(e, false);
    }
  }
}
