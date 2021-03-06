package netizens.bank.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import netizens.bank.server.Client;
import netizens.bank.utils.Debug;
import netizens.bank.utils.Error;
import netizens.bank.utils.JSON;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Server.java
 *
 * This class handles the simple server input and output.
 **/
public class Server{
  private ServerSocket server;

  /**
   * Server()
   *
   * The main server initialisation file.
   *
   * @param mainJSON The main configuration file containing the settings for
   * the server.
   **/
  public Server(JSONTokener mainJSON){
    /* Read Server JSON from configuration */
    JSONObject mainObj = (new JSONObject(mainJSON)).getJSONObject("main");
    /* Get the list of settings files */
    JSONObject settingsObj = mainObj.getJSONObject("settings");
    /* Get the server settings filename */
    String serverPath = settingsObj.getString("server");
    Debug.println("serverPath -> " + serverPath);
    /* Load window settings JSON file */
    JSONObject serverObj = (new JSONObject(JSON.getJSONTokener(serverPath))).getJSONObject("server");
    /* Safely create the server instance */
    try{
      /* Create server */
      server = new ServerSocket(3333);
    }catch(IOException e){
      /* Default error handling */
      Error.safeThrow(e, true);
    }
    /* Loop infinitely */
    for(;;){
      /* Safely deal with client exceptions */
      try{
        /* Internal infinite loop */
        for(;;){
          /* Accept client connection */
          Socket s = server.accept();
          /* Pass connection on and start the thread */
          new Client(s).start();
        }
      }catch(IOException e){
        /* Default error handling */
        Error.safeThrow(e, false);
      }
    }
  }
}
