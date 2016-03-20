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
#define DEBUG FALSE

/* Static declaration of methods */
static bool initHardware();
static bool openDevice();
static bool closeDevice();
static void saveImage();
static void generateHash();
static void checkFinger();
static void enrolFinger();
static void debug(const char* msg);
static void displayHelp();
static void displayVersion();

/* Static declaration of variables */
static struct fp_dscv_dev** devices;
static struct fp_dscv_dev* device;
static struct fp_dev* dev;

/**
 * main()
 *
 * The entry point of the program. The mode of operation must be selected, else
 * a help message will be displayed. The program can only accept one parameter
 * for the purpose of simplicity.
 *
 * @param argc The number of parameters.
 * @param argv The parameters passed to the program.
 **/
int main(int argc, char** argv){
  bool okay = true;
  char opt = 'h';
  #if DEBUG == TRUE
    debug("program started");
  #endif
  /* Check the program for parameters */
  if(argc >= 2){
    /* Check if the parsed parameter has enough characters */
    if(argv[1][0] != '\0'){
      if(argv[1][1] != '\0'){
        /* Set the option to the character */
        opt = argv[1][1];
      }
    }
  }
  /* Only start the devices that require starting */
  switch(opt){
    case 'c' :
    case 'e' :
    case 'g' :
    case 'i' :
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
      break;
    case 'h' :
    case 'v' :
    default  :
      /* Do nothing */
      break;
  }
  /* Do specific task */
  switch(opt){
    case 'c' :
      /* Check finger */
      checkFinger();
      break;
    case 'e' :
      /* Enrol finger */
      enrolFinger();
      break;
    case 'g' :
      /* Generate hash */
      generateHash();
      break;
    case 'h' :
      displayHelp();
      break;
    case 'i' :
      /* Save image */
      saveImage();
      break;
    case 'v' :
      displayVersion();
      break;
    default :
      displayHelp();
      break;
  }
  switch(opt){
    case 'c' :
    case 'e' :
    case 'g' :
    case 'i' :
      okay = closeDevice();
      if(!okay){
        #if DEBUG == TRUE
          debug("failed to close device");
        #endif
          return 0;
      }
      break;
    case 'h' :
    case 'v' :
    default  :
      /* Do nothing */
      break;
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
  fp_exit();
  return okay;
}

/**
 * saveImage()
 *
 * Saves an image of the fingerprint to disk.
 **/
static void saveImage(){
  int r;
  struct fp_img *img = NULL;
  struct fp_img *imgBin = NULL;
  /* Capture image */
  r = fp_dev_img_capture(dev, 0, &img);
  /* Check whether than has been an error */
  if(r != 0){
    /* Indicate issue */
    #if DEBUG == TRUE
      debug("failed to capture image");
    #endif
  }
  /* Save image */
  r = fp_img_save_to_file(img, "finger.pgm");
  /* Check whether than has been an error */
  if(r != 0){
    /* Indicate issue */
    #if DEBUG == TRUE
      debug("failed to save image");
    #endif
  }
  /* Binarize image */
  imgBin = fp_img_binarize(img);
  /* Save binarized image */
  r = fp_img_save_to_file(imgBin, "finger_binarized.pgm");
  /* Free memory */
  fp_img_free(img);
  /* Check whether than has been an error */
  if(r != 0){
    /* Indicate issue */
    #if DEBUG == TRUE
      debug("failed to save standardized image");
    #endif
  }
}

/**
 * generateHash()
 *
 * Generates the hash for a given fingerprint and prints the result to the main
 * output stream.
 **/
static void generateHash(){
//  int r;
//  struct fp_minutiae *mMin = NULL;
//  struct fp_minutia **mList = NULL;
//  struct fp_img *img = NULL;
//  int mLen = 0;
//  int z;
//  int w;
//  int xHash[150];
//  int yHash[150];
//  double rHash[150];
//  int tHash[150];
//  int dHash[150];
//  int hLen = 0;
//  int dist;
//  int local;
//  int lLen;
//  double worst;
//  /* Capture image */
//  r = fp_dev_img_capture(dev, 0, &img);
//  /* Check whether than has been an error */
//  if(r != 0){
//    /* Indicate issue */
//    #if DEBUG == TRUE
//      debug("failed to capture image");
//    #endif
//  }
//  /* Standardize image */
//  fp_img_standardize(img);
//  /* Get minutiae from image to generate the hash */
//  mMin = fp_img_get_minutiae(img);
//  /* Get list */
//  mList = mMin->list;
//  mLen = mMin->num;
//  /* Check whether list is returned */
//  if(mList){
//  /* Debug points collected */
//  #if DEBUG == TRUE
//    printf("count -> %i", mLen);
//    printf("z,x,y,ex,ey,dir,rel,typ,app,ftr\n");
//  #endif
//    /* Loop through the points */
//    for(z = 0; z < mLen; z++){
//      /* Debug points collected */
//      #if DEBUG == TRUE
//        printf("%i,", z);
//        printf("%i,", mList[z]->x);
//        printf("%i,", mList[z]->y);
//        printf("%i,", mList[z]->ex);
//        printf("%i,", mList[z]->ey);
//        printf("%i,", mList[z]->direction);
//        printf("%f,", mList[z]->reliability);
//        printf("%i,", mList[z]->type);
//        printf("%i,", mList[z]->appearing);
//        printf("%i,", mList[z]->feature_id);
//        printf("\n");
//      #endif
//      /**
//       * TODO: For the hash this must be done:
//       *   [/] Remove minutiae that are not reliable enough
//       *   [/] Get mean distance to locals
//       *   [/] Add type
//       *   [ ] Build location based hash [x + (y * width)]
//       **/
//      ///* Save minutiae that are accurate enough */
//      //if(mList[z]->reliability >= 0.8){
//        /* Store important data */
//        xHash[hLen] = mList[z]->ex;
//        yHash[hLen] = mList[z]->ey;
//        rHash[hLen] = mList[z]->reliability;
//        tHash[hLen] = mList[z]->type;
//        /* Increment length of stored data */
//        hLen++;
//      //}
//    }
//    /* Make sure the list is large enough and remove stragglers */
//    if(hLen < 10){
//      /* Indicate error */
//      printf("ERROR\n");
//    }else{
//      while(hLen > 10){
//        /* Remove extra points */
//        worst = 2;
//        /* Position of worst */
//        w = 0;
//        /* Find worst reliability */
//        for(z = 0; z < hLen; z++){
//          if(rHash[z] < worst){
//            /* Store new worst */
//            worst = rHash[z];
//            w = z;
//          }
//        }
//        /* Re-copy array without worst */
//        for(z = 0; z < hLen; z++){
//          /* When worst found, shift copy over */
//          if(z >= w){
//            xHash[z] = xHash[z + 1];
//            yHash[z] = yHash[z + 1];
//            rHash[z] = rHash[z + 1];
//            tHash[z] = tHash[z + 1];
//          }
//        }
//        /* Decrement size of array */
//        hLen--;
//      }
//    }
//    /* Get mean distance to locals */
//    for(z = 0; z < hLen; z++){
//      /* Reset local sum */
//      local = 0;
//      /* Reset local count */
//      lLen = 0;
//      /* Iterate over locals (really hacked for speed!) */
//      for(w = 0; w < hLen; w++){
//        /* TODO: Method not very robust to rotation. */
//        /* Generate distance (hacked) */
//        dist = (xHash[z] + yHash[z]) - (xHash[w] + yHash[w]);
//        dist = dist >= 0 ? dist : -dist;
//        /* If distance is low enough, store neighbour */
//        if(dist <= 50){
//          /* Sum locals */
//          local += dist;
//          /* Increment count */
//          lLen++;
//        }
//        /* Save locals distance */
//        if(lLen > 0){
//          dHash[z] = (int)(local / lLen) >> 3;
//        }else{
//          dHash[z] = 0;
//        }
//      }
//      /* Debug hash data */
//      #if DEBUG == TRUE
//        printf("[%i] RAW -> (%i, %i, %f), %i, %i\n", z, xHash[z], yHash[z], rHash[z], tHash[z], dHash[z]);
//      #endif
//    }
//  }else{
//    /* Debug points collected */
//    #if DEBUG == TRUE
//      /* NULL returned */
//      debug("failed to get minutiae");
//    #endif
//  }
//  /* Free memory */
//  fp_img_free(img);
//  /* Check whether than has been an error */
//  if(r != 0){
//    /* Indicate issue */
//    #if DEBUG == TRUE
//      debug("failed to save standardized image");
//    #endif
//  }
}

/**
 * checkFinger()
 *
 * Checks a finger against one that has been enrolled for a match.
 **/
static void checkFinger(){
  struct fp_print_data *data;
  int r;
  do{
    /* Statically generate image */
    struct fp_img *img = NULL;
    r = fp_print_data_load(dev, RIGHT_INDEX, &data);
    /* If error, return */
    if(r < 0){
      return;
    }
    r = fp_verify_finger_img(dev, data, &img);
    /* If error, return */
    if(r < 0){
      return;
    }
    /* Free image memory */
    fp_img_free(img);
    switch(r){
      case FP_VERIFY_NO_MATCH:
        printf("NO_MATCH\n");
        return;
      case FP_VERIFY_MATCH:
        printf("MATCH\n");
        return;
      case FP_VERIFY_RETRY:
        /* Scan didn't work */
        break;
      case FP_VERIFY_RETRY_TOO_SHORT:
        /* Swipe was too short */
        break;
      case FP_VERIFY_RETRY_CENTER_FINGER:
        /* Finger not centred */
        break;
      case FP_VERIFY_RETRY_REMOVE_FINGER:
        /* Try again required */
        break;
    }
  /* Loop infinitely back to do */
  }while(1);
  /* Free the print data */
  fp_print_data_free(data);
}

/**
 * enrolFinger()
 *
 * Enrols a finger into the finger database.
 **/
static void enrolFinger(){
  struct fp_print_data *enrolled_print = NULL;
  int r;
  /* Scan finger into system */
  do{
    /* Store temporary finger image */
    struct fp_img *img = NULL;
    #if DEBUG == TRUE
      debug("scan your finger now");
    #endif
    /* Enrol the finger image */
    r = fp_enroll_finger_img(dev, &enrolled_print, &img);
    /* Free up the finger memory space */
    fp_img_free(img);
    /* Did we succeed? */
    if(r < 0){
      #if DEBUG == TRUE
        debug("enrol failed with error");
      #endif
      /* Return early */
      return;
    }
    /* Display message on scan success */
    switch(r){
      case FP_ENROLL_COMPLETE:
        #if DEBUG == TRUE
          debug("enrol complete");
        #endif
        break;
      case FP_ENROLL_FAIL:
        #if DEBUG == TRUE
          debug("enrol failed, something went wrong");
        #endif
        break;
      case FP_ENROLL_PASS:
        #if DEBUG == TRUE
          debug("enrol stage passed");
        #endif
        break;
      case FP_ENROLL_RETRY:
        #if DEBUG == TRUE
          debug("scan failed, please try again");
        #endif
        break;
      case FP_ENROLL_RETRY_TOO_SHORT:
        #if DEBUG == TRUE
          debug("swipe too short, please try again");
        #endif
        break;
      case FP_ENROLL_RETRY_CENTER_FINGER:
        #if DEBUG == TRUE
          debug("please centre finger and try again");
        #endif
        break;
      case FP_ENROLL_RETRY_REMOVE_FINGER:
        #if DEBUG == TRUE
          debug("scan failed, please try again");
        #endif
        break;
    }
  /* Keep getting fingers until completed */
  }while(r != FP_ENROLL_COMPLETE);
  /* This error should not be thrown */
  if(!enrolled_print){
    fprintf(stderr, "Enrol complete but no print?\n");
  }
  /* Save enrolled print */
  r = fp_print_data_save(enrolled_print, RIGHT_INDEX);
  /* Did we succeed? */
  if(r < 0){
    #if DEBUG == TRUE
      debug("enrol failed with error");
    #endif
    /* Return early */
    return;
  }
  /* Free print data */
  fp_print_data_free(enrolled_print);
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

/**
 * displayHelp()
 *
 * Displays the help for this program.
 **/
static void displayHelp(){
  printf("\n");
  printf("  finger.bin [OPT]\n");
  printf("\n");
  printf("  OPTions\n");
  printf("\n");
  printf("    -c    Check fingerprint\n");
  printf("    -e    Enrol fingerprints\n");
  printf("    -g    Generate a fingerprint hash\n");
  printf("    -h    Display this message\n");
  printf("    -i    Generate a fingerprint image\n");
  printf("    -v    Display version\n");
  printf("\n");
}

/**
 * displayVersion()
 *
 * Displays the version for this program.
 **/
static void displayVersion(){
  printf("Version 1.0.0\n");
}
