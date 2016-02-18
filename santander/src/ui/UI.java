package netizens.bank.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import netizens.bank.Main;
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
  private int guiWidth, guiHeight;
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
    guiWidth = windowObj.getInt("width");
    guiHeight = windowObj.getInt("height");
    gui.setSize(guiWidth, guiHeight);
    gui.setLocationRelativeTo(null);
    /* Do not resize once setup */
    gui.setResizable(false);
    /* Don't display any window rubbish */
    gui.setUndecorated(true);
    /* Stop the GUI trying to do it's own layout management */
    gui.getContentPane().setLayout(null);

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
      switch(type){
        case "label" :
          /* Create a JLabel and add text to it */
          JLabel jLabel = new JLabel(elems.getString("text"), SwingConstants.CENTER);
          /* Must set opaque for the background colour to work */
          jLabel.setOpaque(true);
          /* Read and set the colours */
          jLabel.setForeground(Colour.cast(elems.getString("colour")));
          jLabel.setBackground(Colour.cast(elems.getString("back")));
          jLabel.setBounds(
            (int)(elems.getDouble("left") * guiWidth),
            (int)(elems.getDouble("top") * guiHeight),
            (int)(elems.getDouble("width") * guiWidth),
            (int)(elems.getDouble("height") * guiHeight)
          );
          /* Choose a font size that work for the allocated space */
          /* NOTE: http://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size#2715279 */
          Font labelFont = jLabel.getFont();
          String labelText = jLabel.getText();
          int stringWidth = jLabel.getFontMetrics(labelFont).stringWidth(labelText);
          double widthRatio = (double)(jLabel.getWidth()) / (double)stringWidth;
          int newFontSize = (int)(labelFont.getSize() * widthRatio);
          int fontSizeToUse = Math.min(newFontSize, jLabel.getHeight());
          jLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
          /* Add the label to the frame */
          gui.add(jLabel);
          break;
        default :
          System.err.println("`" + type + "` type not specified.");
          Main.exit(Main.EXIT_STATUS.ERROR);
          break;
      }
    }
  }
}
