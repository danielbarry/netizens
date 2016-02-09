package netizens.bank.utils;

/**
 * Clock.java
 *
 * This class is responsible for handling and keeping track of all time related
 * tasks.
 **/
public class Clock{
  private static long startTime = getTime();

  /**
   * getTime()
   *
   * Gets the current time in milliseconds.
   *
   * @return The current time in milliseconds.
   **/
  public static long getTime(){
    return System.currentTimeMillis();
  }

  /**
   * getTimeSinceStart()
   *
   * Gets the current time in milliseconds relative to when the program was
   * started.
   *
   * @return The current time since the start.
   **/
  public static long getTimeSinceStart(){
    return getTime() - startTime;
  }
}
