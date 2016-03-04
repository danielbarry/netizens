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
static bool openDevice();
static bool closeDevice();
static void debug(const char* msg);

/* Static declaration of variables */
static struct fp_dscv_dev** devices;
static struct fp_dscv_dev* device;
static struct fp_dev* dev;

/**
 * main()
 *
 * The entry point of the program.
 *
 * @param argc The number of parameters.
 * @param argv The parameters passed to the program.
 **/
int main(int argc, char** argv){
  bool okay = true;
  #if DEBUG == TRUE
    debug("program started");
  #endif
  /* Ready hardware */
  okay = initHardware();
  if(!okay){
    #if DEBUG == TRUE
      debug("hardware initialisation failed");
    #endif
      return 0;
  }
  okay = openDevice();
  if(!okay){
    #if DEBUG == TRUE
      debug("failed to open device");
    #endif
      return 0;
  }
  okay = closeDevice();
  if(!okay){
    #if DEBUG == TRUE
      debug("failed to close device");
    #endif
      return 0;
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
      #if DEBUG == TRUE
        debug("failed to discover devices");
      #endif
    }
  }
  /* Device check */
  if(ready){
    /* Pick the first device */
    device = devices[0];
    /* Make sure the device exists */
    if(!device){
      /* Indicate issue */
      ready = false;
      #if DEBUG == TRUE
        debug("failed to find device");
      #endif
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
  /* Return whether hardware is ready */
  return ready;
}

/**
 * openDevice()
 *
 * Opens the device ready for use.
 *
 * @return Whether the device was able to open.
 **/
static bool openDevice(){
  bool okay = true;
  /* Open device for reading */
  if(okay){
    /* Lock device */
    dev = fp_dev_open(device);
    /* Free up other devices */
    fp_dscv_devs_free(devices);
    /* Make sure device opened correctly */
    if(!dev){
      /* Indicate issue */
      okay = false;
      #if DEBUG == TRUE
        debug("failed to open device");
      #endif
    }
  }
  return okay;
}

/**
 * closeDevice()
 *
 * Closes the device for other systems to use.
 *
 * @return Whether the device was able to close.
 **/
static bool closeDevice(){
  bool okay = true;
  fp_dev_close(dev);
  return okay;
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
