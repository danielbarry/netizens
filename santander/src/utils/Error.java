package netizens.bank.utils;

/**
 * Error.java
 *
 * Handles all exceptions thrown and prints a meaningful message.
 **/
public class Error{
  /**
   * safeThrow()
   *
   * Safely displays an error message showing what went wrong in the program.
   *
   * @param exception An exception instance that has been caught to be thrown
   * safely.
   * @param critical A boolean indication whether the error was critical enough
   * to stop operation, in which case the program will stop.
   **/
  public static void safeThrow(Exception exception, boolean critical){
    /* Print a meaningful message */
    System.out.println(
      "ERROR OCCURRED" +
      "\n" +
      exception.toString() +
      "\n" +
      exception.getStackTrace()
    );
    /* Check whether error was critical */
    if(critical){
      System.exit(0);
    }
  }
}
