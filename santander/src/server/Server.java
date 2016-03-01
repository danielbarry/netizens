package netizens.bank.server;

import netizens.bank.utils.Debug;
import netizens.bank.utils.JSON;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Server.java
 *
 * This class handles the simple server input and output.
 **/
public class Server{
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
  }
}
