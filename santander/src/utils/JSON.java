package netizens.bank.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import netizens.bank.Main;
import netizens.bank.utils.Debug;
import org.json.JSONTokener;
import org.json.JSONObject;

/**
 * JSON.java
 *
 * Acts as a wrapper for correctly loading JSON from a file.
 **/
public class JSON{
  /**
   * getJSONTokener()
   *
   * This method acts as a wrapper to safely load configuration files as a
   * tokenized object.
   *
   * @param filepath The file to be loaded.
   * @return The tokenized object.
   **/
  public static JSONTokener getJSONTokener(String filepath){
    /* Pre-define file variables with null */
    File file = null;
    InputStream in = null;
    /* Try to load in file */
    try{
      file = new File(filepath);
      in = new FileInputStream(file);
    }catch(FileNotFoundException e){
      System.err.println("Config not found");
      Main.exit(Main.EXIT_STATUS.ERROR);
    }
    /* Make sure that we definitely caught the exception */
    if(file != null && in != null){
      Debug.println("Parsing String to JSONTokener");
      /* Load main configuration file and return it */
      return new JSONTokener(in);
    }else{
      /* Default action is to return null */
      return null;
    }
  }
}
