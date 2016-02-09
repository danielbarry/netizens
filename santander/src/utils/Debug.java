package netizens.bank.utils;

/**
 * Debug.java
 *
 * This class offers debug options to be used in the terminal.
 **/
public class Debug{
  private static final String DEBUG_HEAD = "[>>] ";

  /**
   * print()
   *
   * Prints a message without a newline to the terminal.
   *
   * @param msg The message to be printed to the terminal.
   **/
  public static void print(String msg){
    System.out.print(DEBUG_HEAD + msg);
  }

  /**
   * println()
   *
   * Prints a message with a newline to the terminal.
   *
   * @param msg The message to be printed to the terminal.
   **/
  public static void println(String msg){
    System.out.println(DEBUG_HEAD + msg);
  }
}
