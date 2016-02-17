package netizens.bank.ui;

import java.util.HashMap;
import javax.swing.JFrame;
import netizens.bank.utils.Debug;
import netizens.bank.utils.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * UI.java
 *
 * This class is responsible for handling the User Interface in its entirety.
 **/
public class UI{
  private JFrame gui;
  private HashMap<String, JSONArray> allDisplays;

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

    /* Load displays from array */
    JSONArray displays = windowObj.getJSONArray("displays");
    /* Create HashMap */
    allDisplays = new HashMap<String, JSONArray>();
    /* Iterate over displays */
    for(int x = 0; x < displays.length(); x++){
      /* Single display object */
      JSONObject disp = displays.getJSONObject(x);
      /* Add display window */
      allDisplays.put(disp.getString("name"), disp.getJSONArray("elems"));
    }

    /* Setup GUI based on values read */
    gui = new JFrame();
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setTitle(windowObj.getString("title"));
    gui.setSize(windowObj.getInt("width"), windowObj.getInt("height"));
    gui.setLocationRelativeTo(null);

    /* Load in main display */
    loadDisplay("main");

    /* Finally display */
    gui.setVisible(true);
  }

  /**
   * loadDisplay()
   *
   * Load the display loaded in from predefined list.
   *
   * @param name The name of the display to be loaded.
   **/
  private void loadDisplay(String name){
    /* TODO: Dump previous display. */
    /* Get the requested window */
    JSONArray disp = allDisplays.get(name);
    /* Iterate over elements of display */
    for(int x = 0; x < disp.length(); x++){
      /* Get the elements */
      JSONObject elems = disp.getJSONObject(x);
      /* Get the type to decide what to do next */
      String type = elems.getString("type");
      Debug.println("type -> " + type);
    }
  }
}
