package netizens.bank;

/**
 * Main.java
 *
 * Entry class into the program, responsible for dealing with any input
 * parameters to the program.
 **/
public class Main{
  public enum EXIT_STATUS{
    UNKNOWN, PLANNED, UNPLANNED, ERROR;
  }

  /**
   * main()
   *
   * Entry point into the Java program.
   *
   * @param args The arguments accepted from the command line.
   **/
  public static void main(String[] args) throws InterruptedException{
    Runtime.getRuntime().addShutdownHook(
      new Thread(){
        @Override
        public void run(){
          exit(EXIT_STATUS.UNPLANNED);
        }
      }
    );
  }

  /**
   * exit()
   *
   * Exits the program appropriately depending on how the program was asked to
   * shutdown.
   *
   * @param status This lets the exit code know how long it has to shutdown. If
   * the shutdown doesn't have much time, only core tasks should be done before
   * shutting down.
   **/
  public static void exit(EXIT_STATUS status){
    switch(status){
      case PLANNED :
        /* TODO: Shutdown resources as safely as possible, we have all the time
         *       in the world. */
        break;
      case ERROR :
        /* TODO: Shutdown resources safely, but bare in mind that they may not
         *       respond correctly due to the error that occurred. After that,
         *       print error and flush all messages. */
        break;
      case UNKNOWN :
      case UNPLANNED :
      default :
        /* TODO: Close all important resources as fast as possible. */
        break;
    }
  }
}
