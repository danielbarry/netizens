package netizens.bank.hardware;

import netizens.bank.hardware.IO_TYPE;

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
   * @return The String value to be hashed, otherwise null.
   **/
  public static String getHash(IO_TYPE type){
    /* Generate default hash case */
    String hash = null;
    /* Generate hash depending on IO type */
    switch(type){
      case FINGERPRINT :
        /* TODO: Generate hash. */
        break;
    }
    /* Return generated hash */
    return hash;
  }
}
