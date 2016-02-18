package netizens.bank.ui;

import java.awt.Color;

/**
 * Colour.java
 *
 * A simple class for casting colours to an accepted format.
 **/
public class Colour{
  /**
   * cast()
   *
   * This method is responsible for casting multiple different types of String
   * to a Color object.
   *
   * @param colour The colour to be casted to a colour object.
   * @return A casted colour that can be used in the UI.
   **/
  public static Color cast(String colour){
    /* Is the value the size of a HEX value? */
    if(colour.length() == 7){
      /* Check the first character */
      if(colour.charAt(0) == '#'){
        /* Cast the value to a Color object */
        return new Color(
          hexToInt(colour.substring(1, 3)),
          hexToInt(colour.substring(3, 5)),
          hexToInt(colour.substring(5, 7))
        );
      }
    }
    return Color.BLACK;
  }

  /**
   * hexToInt()
   *
   * Converts a hexadecimal String to an Integer, between the value of 0 and
   * 255.
   *
   * NOTE: This method offers no error detection in values parsed.
   *
   * @param hex The String to be converted.
   * @return The final value.
   **/
  private static int hexToInt(String hex){
    int result = 0;
    for(int x = 0; x < hex.length(); x++){
      result = result << 4;
      int temp = (int)(hex.charAt(x));
      result += temp <= (int)('9') ? temp - (int)('0') : temp - (int)(':');
    }
    return result;
  }
}
