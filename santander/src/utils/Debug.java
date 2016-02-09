package netizens.bank.utils;

import netizens.bank.utils.Clock;

/**
 * Debug.java
 *
 * This class offers debug options to be used in the terminal.
 **/
public class Debug{
  /**
   * print()
   *
   * Prints a message without a newline to the terminal.
   *
   * @param msg The message to be printed to the terminal.
   **/
  public static void print(String msg){
    System.out.print(getDebugHead() + msg);
  }

  /**
   * println()
   *
   * Prints a message with a newline to the terminal.
   *
   * @param msg The message to be printed to the terminal.
   **/
  public static void println(String msg){
    System.out.println(getDebugHead() + msg);
  }

  /**
   * getDebugHead()
   *
   * Gets the information to be printed at the start of the document.
   *
   * @return The header debug String.
   **/
  private static String getDebugHead(){
    return "[" + Clock.getTimeSinceStart() + "] ";
  }
}
