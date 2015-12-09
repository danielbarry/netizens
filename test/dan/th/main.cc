#include "main.hh"

#include <iomanip>
#include <iostream>

#define BUFFER_SIZE 20
#define DEFAULT_SIZE 1024

/* Buffer Exploit */
static char buffer[BUFFER_SIZE];

/**
 * main()
 *
 * The main entry point into the program.
 *
 * @param argc The number of arguments to the program.
 * @param argv The arguments parsed to the program.
 * @return The exit status of the program.
 **/
int main(int argc, char **argv){
  /* Main used variables */
  unsigned int mode = 0;
  int start = 0 /* Put position of buffer here */;
  unsigned int size = DEFAULT_SIZE;
  char opt;
  /* Temporary variables */
  int tmpCnt = 0;
  /* Process arguments */
  for(int x = 1; x < argc; x++){
    if(argv[x][0] == '-'){
      opt = 'h';
      if(sizeof(argv[x]) / sizeof(char) >= 2)
        opt = argv[x][1];
      switch(opt){
        mode = -1;
        case 'a' :
          Main::print("TalentlessHack");
          Main::print("");
          Main::print("  @author : B[]");
          Main::print("  @version: v0.0.1");
          Main::print("");
          Main::print("  Desc: A simple memory exploit program designed to");
          Main::print("        exploit the Linux memory system.");
          Main::print("");
          Main::print("  Aims: This program aims to demonstrate what a user");
          Main::print("        with non-root privileges may find out about a");
          Main::print("        system's RAM.");
          Main::print("");
          Main::print("  Bugs: Please report bugs to the repository you find");
          Main::print("        this code.");
          Main::print("");
          Main::print("  Laws: Use this code however you want WITHOUT");
          Main::print("        WARRANTY. This program should be used at the");
          Main::print("        user's own risk.");
          break;
        case 'h' :
          mode = -1;
          Main::print("TalentlessHack [OPT] [TYPE]");
          Main::print("");
          Main::print("  OPTions");
          Main::print("    -a    About this program");
          Main::print("    -h    Displays this help");
          Main::print("    -s    Set size for mode");
          Main::print("            Def: 1024");
          Main::print("");
          Main::print("  TYPE");
          Main::print("     0    Buffer Overflow");
          Main::print("");
          Main::print("    Default: 0");
          break;
        case 's' :
          x++;
          size = 0;
          tmpCnt = 0;
          while(argv[x][tmpCnt] != '\0'){
            size *= 10;
            size += argv[x][tmpCnt] - '0';
            tmpCnt++;
          }
          break;
        default  :
          mode = -1;
          Main::print("Unrecognised input.");
      }
    }else{
      opt = argv[x][0];
      switch(opt){
        case '0' :
          mode = 0;
          break;
        case '1' :
          mode = 1;
          break;
        default  :
          mode = -1;
          Main::print("Unrecognised mode.");
      }
    }
  }
  /* Print key variables */
  std::cout << "Mode:\t" << mode << std::endl;
  std::cout << "Start:\t" << start << std::endl;
  std::cout << "Size:\t" << size << std::endl;
  /* Start exploit */
  switch(mode){
    /* No mode selected on purpose */
    case -1 :
      break;
    case 0  :
      for(int x = 0; x < size; x++){
        /* Lock in value from RAM */
        buffer[x] = buffer[x];
        /* Print value to terminal */
        Main::printLoc((int*)&buffer[x], buffer[x]);
      }
      break;
    case 1  :
      for(int x = start; x < size + start; x++){
        /* Print value to terminal */
        Main::printLoc((int*)x, *((int*)x));
      }
      break;
    default :
      Main::print("Mode not implemented.");
  }
  /* Exit good */
  return 0;
}

void Main::print(const char* msg){
  std::cout << msg << std::endl;
}

void Main::printLoc(int* loc, char val){
  std::cout << std::hex << loc;
  std::cout << "\t";
  std::cout << std::hex << (int)val;
  std::cout << "\t";
  std::cout << val << std::endl;
}
