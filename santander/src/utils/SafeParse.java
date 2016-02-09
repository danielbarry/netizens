package netizens.bank.utils;

/**
 * SafeParse.java
 *
 * This class allows values to be safely parsed to other values, mainly String
 * to other types.
 **/
public class SafeParse{
  /**
   * getInt()
   *
   * Gets an integer from a String. If it cannot be parsed, the default value
   * is used.
   *
   * @param value The String that contains the integer to be converted.
   * @param def The integer value to fall back on if the conversion fails.
   * @return The result of the conversion.
   **/
  public static int getInt(String value, int def){
    try{
      return Integer.parseInt(value);
    }catch(NumberFormatException e){
      return def;
    }
  }
}
