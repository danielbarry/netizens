package netizens.bank.hardware;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import netizens.bank.hardware.IO_TYPE;
import netizens.bank.utils.Error;

/**
 * Hardware.java
 *
 * This class is responsible for generating hashes very hardware of given
 * types.
 **/
public class Hardware{
  /**
   * getHash()
   *
   * Gets the hash for a given type of hardware attached.
   *
   * @param type The type of the hardware to be read.
   * @return The String value to be hashed, otherwise NULL.
   **/
  public static String getHash(IO_TYPE type){
    /* Generate default hash case */
    String hash = null;
    /* Generate hash depending on IO type */
    switch(type){
      case FINGERPRINT :
        hash = getFingerHash();
        break;
    }
    /* Return generated hash */
    return hash;
  }

  /**
   * getFingerHash()
   *
   * Gets the hash of the finger print from the external program.
   *
   * @return The hash of the fingerprint, else NULL.
   **/
  private static String getFingerHash(){
    /* Run the program */
    String result = runProgram(
      new String[]{
        "./finger.bin",
        "-h"
      }
    );
    System.out.println(result); // TODO: Remove me.
    /* TODO: Process the result. */
    return result;
  }

  /**
   * runProgram()
   *
   * Runs the program specified and executes it. The output of the program is
   * returned when the program is complete.
   *
   * @param program The program to be executed, complete with the arguments.
   * @return The output of the program.
   **/
  private static String runProgram(String[] program){
    /* Assign variable */
    Process process = null;
    /* Safely attempt program start */
    try{
      /* Start our process */
      process = new ProcessBuilder(program).start();
    }catch(IOException e){
      /* Default error handling */
      Error.safeThrow(e, false);
    }
    /* Handle NULL after bad program start */
    if(process == null){
      /* Bad return case */
      return null;
    }
    /* Get the output stream from the process */
    InputStream is = process.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    /* Read the lines from the process */
    String buffer = "";
    String line;
    /* Safely read program lines */
    try{
      while((line = br.readLine()) != null){
        /* Store the line in the buffer */
        buffer += line;
      }
    }catch(IOException e){
      /* Default error handling */
      Error.safeThrow(e, false);
    }
    /* Return the result of the buffer */
    return buffer;
  }
}
