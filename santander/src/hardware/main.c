/**
 * main.c
 *
 * This class handles obtaining a hash of a fingerprint and displaying this
 * over the terminal. No other information should be printed to the terminal so
 * the program output may be wrapped in normal operation.
 *
 * Program written by B[]
 **/

/* NOTE: Below is required for libfprint to compile. */
#include <stdlib.h>
/* NOTE: This is found at /usr/include/libfprint/fprint.h. */
#include "libfprint/fprint.h"
/* NOTE: Required for understanding bool. */
#include <stdbool.h>
/* For printf */
#include <stdio.h>

/* Declarations */
#define TRUE 1
#define FALSE 0

/* Configuration */
#define DEBUG TRUE

/* Static declaration of methods */
static bool initHardware();
static void debug(const char* msg);

/**
 * main()
 *
 * The entry point of the program.
 *
 * @param argc The number of parameters.
 * @param argv The parameters passed to the program.
 **/
int main(int argc, char** argv){
  #if DEBUG == TRUE
    debug("program started");
  #endif
  /* Ready hardware */
  bool ready = initHardware();
  if(ready){
    #if DEBUG == TRUE
      debug("hardware ready");
    #endif
  }else{
    #if DEBUG == TRUE
      debug("hardware not ready");
    #endif
  }
  #if DEBUG == TRUE
    debug("program ended");
  #endif
  /* Return default value */
  return 0;
}

/**
 * initHardware()
 *
 * Initialise the hardware for the system and return whether this has been done
 * correctly.
 *
 * @return Whether the hardware is ready to be used.
 **/
static bool initHardware(){
  bool ready = true;
  int r = 1;
  struct fp_dscv_dev** devices;
  struct fp_dscv_dev* device;
  struct fp_driver* driver;
  /* Initialise fprint */
  if(ready){
    r = fp_init();
    if(r < 0){
      /* Indicate issue */
      ready = false;
      #if DEBUG == TRUE
        debug("failed to initialise");
      #endif
    }
  }
  #if DEBUG == TRUE
    /* Setup some debug information */
    if(ready){
      fp_set_debug(3);
    }
  #endif
  /* Device discovery */
  if(ready){
    /* Get a list of device */
    devices = fp_discover_devs();
    /* Find out whether we got any devices */
    if(!devices){
      /* Indicate issue */
      ready = false;
    }else{
      /* Pick the first device */
      device = devices[0];
      /* Make sure the device exists */
      if(!device){
        /* Indicate issue */
        ready = false;
      }else{
        /* Get driver information */
        driver = fp_dscv_dev_get_driver(device);
        #if DEBUG == TRUE
          /* Display information about the driver */
          debug("found device[0] driver ->");
          debug(fp_driver_get_full_name(driver));
        #endif
      }
    }
  }
  /* Return whether hardware is ready */
  return ready;
}

/**
 * debug()
 *
 * Debug messages from this program which can be switch on and off using the
 * pre-compiler settings.
 *
 * @param msg The message to be displayed.
 **/
#if DEBUG == TRUE
  static void debug(const char* msg){
    printf("[??] %s \n", msg);
  }
#endif
