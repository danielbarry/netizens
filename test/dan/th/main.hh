/**
 * Main
 *
 * The main class which is responsible for starting the program.
 **/

#ifndef MAIN_HH
  #define MAIN_HH
  class Main{
    public:
      /**
       * print()
       *
       * Print a message to the terminal.
       *
       * @param msg The message to be printed to the terminal.
       **/
      static void print(const char* msg);

      /**
       * printLoc()
       *
       * Prints a character found at a given location.
       *
       * @param loc The location of where the value was found.
       * @param val The value found at a given location.
       **/
      static void printLoc(int* loc, char val);
  };
#endif
