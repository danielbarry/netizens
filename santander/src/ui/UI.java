package netizens.bank.ui;

import javax.swing.JFrame;

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
  public UI(int width, int height){
    gui = new JFrame();
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    /* TODO: Read title from JSON */
    gui.setTitle("Bank UI");
    gui.setSize(width, height);
    gui.setLocationRelativeTo(null);
    gui.setVisible(true);
  }
}
