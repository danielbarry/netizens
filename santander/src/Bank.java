package netizens.bank;

import netizens.bank.Main;
import netizens.bank.ui.UI;
import netizens.bank.utils.Debug;

/**
 * Bank.java
 *
 * This class is responsible for initialising the various sections, depending
 * on whether parameters are being parsed to the front-end or back-end.
 **/
public class Bank{
  public enum MODE{
    NONE, ERROR, HELP, VERSION, UI, SERVER
  }

  /**
   * Bank()
   *
   * Here we look at distributing the various tasks to where they belong and
   * handling immediate requests.
   *
   * @param args The arguments give to the program.
   **/
  public Bank(String[] args){
    /* Store information that may be helpful later */
    MODE mode = MODE.NONE;
    /* Firstly, parse all arguments and draw all the data from them */
    for(int x = 0; x < args.length; x++){
      /* Is the string large enough to check? */
      if(args[x].length() > 0){
        /* Is this an argument? */
        if(args[x].charAt(0) == '-'){
          /* Is the next section large enough to check? */
          if(args[x].length() > 1){
            /* Is this a small argument or full argument? */
            if(args[x].charAt(1) == '-'){
              /* Convert the values */
              switch(args[x].substring(2)){
                case "help" :
                  args[x] = "-h";
                  break;
                case "server" :
                  args[x] = "-s";
                  break;
                case "ui" :
                  args[x] = "-u";
                  break;
                case "version" :
                  args[x] = "-v";
                  break;
              }
            }
            /* Check the values */
            switch(args[x].charAt(1)){
              case 'h' :
                /* Override any mode other than error */
                if(mode != MODE.ERROR){
                  mode = MODE.HELP;
                }
                break;
              case 's' :
                /* Only override if no other mode set */
                if(mode == MODE.NONE){
                  mode = MODE.SERVER;
                }
                break;
              case 'u' :
                /* Only override if no other mode set */
                if(mode == MODE.NONE){
                  mode = MODE.UI;
                }
                break;
              case 'v' :
                /* Override any mode other than error */
                if(mode != MODE.VERSION){
                  mode = MODE.VERSION;
                }
                break;
              default :
                /* If there has been an issue, go into error mode */
                mode = MODE.ERROR;
                break;
            }

          }
        }
      }
    }
    /* Complete actions for final mode */
    switch(mode){
      case HELP :
        help();
        Main.exit(Main.EXIT_STATUS.PLANNED);
        break;
      case SERVER :
        /* TODO: Write this section. */
        break;
      case UI :
        /* TODO: Write this section. */
        break;
      case VERSION :
        version();
        Main.exit(Main.EXIT_STATUS.PLANNED);
        break;
      case NONE :
        System.err.println("Program was not asked to perform a task.");
        break;
      case ERROR :
        System.err.println("Error in parameter.");
      default :
        Main.exit(Main.EXIT_STATUS.ERROR);
        break;
    }
    Debug.println("Parse arguments");
  }

  /**
   * run()
   *
   * Runs the modules specified by the command line parameters.
   **/
  public void run(){
    /* TODO: Run the modules we found in the command line parameters. */
    Debug.println("Run modules");
  }

  /**
   * help()
   *
   * Displays the help for the program.
   **/
  private void help(){
    System.out.println(
      "\nPROGRAM <MODE> [OPT]" +
      "\n" +
      "\n  MODE" +
      "\n" +
      "\n    --help      -h" +
      "\n      Displays this help." +
      "\n" +
      "\n    --server    -s" +
      "\n      OPTions" +
      "\n        TODO: To be written." +
      "\n" +
      "\n    --ui        -u" +
      "\n      OPTions" +
      "\n        TODO: To be written." +
      "\n    --version   -v" +
      "\n      Displays program version." +
      "\n" +
      "\n  Examples" +
      "\n" +
      "\n    TODO: Write examples."
    );
  }

  /**
   * version()
   *
   * Displays the version of the program.
   **/
  private void version(){
    /* TODO: Get the version from JSON. */
    System.out.println(
      "Version 0.0.0"
    );
  }
}
