package netizens.bank.ui;

import javax.swing.JFrame;
import netizens.bank.utils.Debug;
import netizens.bank.utils.JSON;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * UI.java
 *
 * This class is responsible for handling the User Interface in its entirety.
 **/
public class UI{
  JFrame gui;

  /**
   * UI()
   *
   * The constructor for the UI class, responsible for initialising all values
   * required and starting the UI.
   **/
  public UI(JSONTokener mainJSON){
    /* Read UI JSON from configuration */
    JSONObject mainObj = (new JSONObject(mainJSON)).getJSONObject("main");
    /* Get the list of settings files */
    JSONObject settingsObj = mainObj.getJSONObject("settings");
    /* Get the window settings filename */
    String windowPath = settingsObj.getString("window");
    Debug.println("windowPath -> " + windowPath);
    /* Load window settings JSON file */
    JSONObject windowObj = (new JSONObject(JSON.getJSONTokener(windowPath))).getJSONObject("window");
    /* Setup GUI based on values read */
    gui = new JFrame();
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setTitle(windowObj.getString("title"));
    gui.setSize(windowObj.getInt("width"), windowObj.getInt("height"));
    gui.setLocationRelativeTo(null);
    gui.setVisible(true);
  }
}
