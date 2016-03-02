package netizens.bank.server;

import java.io.InputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import netizens.bank.utils.Error;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Client.java
 *
 * The client as accepted by the sever is handled on an independent thread and
 * processed before replying.
 **/
public class Client extends Thread{
  private static int timeout = 5000;
  private static int readSize = 4096;
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
    /* Safely set time out on request to prevent malicious code */
    try{
      socket.setSoTimeout(timeout);
    }catch(SocketException e){
      /* Default error handling */
      Error.safeThrow(e, false);
    }
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
    JSONTokener jToke = new JSONTokener(raw);
    /* Create JSONObject */
    JSONObject jObj = new JSONObject(jToke);
    /* TODO: Handle JSON. */
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
