package netizens.bank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import netizens.bank.Main;
import netizens.bank.ui.UI;
import netizens.bank.utils.Debug;
import netizens.bank.utils.JSON;
import netizens.bank.utils.SafeParse;
import org.json.JSONTokener;

/**
 * Bank.java
 *
 * This class is responsible for initialising the various sections, depending
 * on whether parameters are being parsed to the front-end or back-end.
 **/
public class Bank{
  public enum MODE{
    NONE, ERROR, HELP, VERSION, UI
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
    /* Store mode */
    MODE mode = MODE.NONE;
    /* Variables parsed */
    String mainFile = "main.json";
    /* Firstly, parse all arguments and draw all the data from them */
    for(int cnt = 0; cnt < args.length; cnt++){
      /* Is the string large enough to check? */
      if(args[cnt].length() > 0){
        /* Is this an argument? */
        if(args[cnt].charAt(0) == '-'){
          /* Is the next section large enough to check? */
          if(args[cnt].length() > 1){
            /* Is this a small argument or full argument? */
            if(args[cnt].charAt(1) == '-'){
              /* Convert the values */
              switch(args[cnt].substring(2)){
                case "config" :
                  args[cnt] = "-c";
                  break;
                case "help" :
                  args[cnt] = "-h";
                  break;
                case "ui" :
                  args[cnt] = "-u";
                  break;
                case "version" :
                  args[cnt] = "-v";
                  break;
              }
            }
            /* Check the values */
            switch(args[cnt].charAt(1)){
              case 'c' :
                /* Check if value could exist */
                if(++cnt < args.length){
                  /* Override the default configuration location */
                  mainFile = args[cnt];
                }else{
                  System.err.println("No arguments for width");
                  Main.exit(Main.EXIT_STATUS.ERROR);
                }
                break;
              case 'h' :
                /* Override any mode other than error */
                if(mode != MODE.ERROR){
                  mode = MODE.HELP;
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
    /* Attempt to load the main settings file */
    JSONTokener mainJSON = JSON.getJSONTokener(mainFile);
    /* Complete actions for final mode */
    switch(mode){
      case HELP :
        help();
        Main.exit(Main.EXIT_STATUS.PLANNED);
        break;
      case UI :
        new UI(mainJSON);
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
      "\n    --ui        -u" +
      "\n      OPTions" +
      "\n        --config    -c" +
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
