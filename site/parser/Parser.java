import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Parser.java
 *
 * The parser for the netizens website, responsible for building all of the web
 * pages for the server offline.
 **/
public class Parser{
  /**
   * Config.Parser.java
   *
   * The configuration for the parser.
   **/
  private static class Config{
    private static final int FAIL = 0, EMPTY = 1, BEGIN = 2, SUB1 = 3,
      SUB2 = 4, SUB3 = 5, END = 6;

    private int mode = FAIL;
    private String[] match;
    private String[] replace;

    /**
     * Config()
     *
     * Build the configuration setting.
     *
     * @param line The line to be parsed.
     **/
    public Config(String line){
      /* Split the data up */
      String[] values = line.split(",");
      /* Parse the configuration */
      switch(values[0]){
        case "empty" :
          mode = EMPTY;
          match = new String[]{};
          replace = new String[]{
            values[1],
            values.length >= 3 ? values[2] : null
          };
          break;
        case "begin" :
          mode = BEGIN;
          match = new String[]{ values[1] };
          replace = new String[]{ values[2], values[3] };
          break;
        case "sub_1" :
          mode = SUB1;
          match = new String[]{ values[1] };
          replace = new String[]{ values[2] };
          break;
        case "sub_2" :
          mode = SUB2;
          match = new String[]{ values[1], values[3] };
          replace = new String[]{ values[2], values[4] };
          break;
        case "sub_3" :
          mode = SUB3;
          match = new String[]{ values[1], values[3], values[5] };
          replace = new String[]{ values[2], values[4], values[6] };
          break;
        case "end" :
          mode = END;
          match = new String[]{ values[1] };
          replace = new String[]{ values[2], values[3] };
          break;
        default :
          Parser.error("invalid mode");
          break;
      }
    }

    /**
     * parse()
     *
     * Parse a markdown line into HTML.
     *
     * @param raw The raw markdown line to be parsed.
     * @return The parsed line into HTML.
     **/
    public String parse(String raw){
      String parsed = raw;
      int first;
      int second;
      int third;
      int diff;
      switch(mode){
        case EMPTY :
          if(parsed.length() == 0){
            parsed = replace[0];
            if(replace[1] != null){
              parsed += replace[1];
            }
          }
          break;
        case BEGIN :
          if(parsed.indexOf(match[0]) == 0){
            parsed =
              replace[0] +
              parsed.substring(match[0].length(), parsed.length()) +
              replace[1];
          }
          break;
        case SUB1 :
          parsed = parsed.replaceAll(match[0], replace[0]);
          break;
        case SUB2 :
          first = -1;
          second = first;
          while((first = parsed.indexOf(match[0], first + 1)) >= 0){
            second = parsed.indexOf(match[1], first + 1);
            if(first < second){
              parsed =
                parsed.substring(0, first) +
                replace[0] +
                parsed.substring(first + match[0].length());
              diff = replace[0].length() - match[0].length();
              parsed =
                parsed.substring(0, second + diff) +
                replace[1] +
                parsed.substring(second + match[1].length() + diff);
            }
          }
          break;
        case SUB3 :
          first = -1;
          second = first;
          third = second;
          while((first = parsed.indexOf(match[0], first + 1)) >= 0){
            second = parsed.indexOf(match[1], first + 1);
            third = parsed.indexOf(match[2], second + 1);
            if(first < second && second < third){
              parsed =
                parsed.substring(0, first) +
                replace[0] +
                parsed.substring(first + match[0].length());
              diff = replace[0].length() - match[0].length();
              parsed =
                parsed.substring(0, second + diff) +
                replace[1] +
                parsed.substring(second + match[1].length() + diff);
              diff += replace[1].length() - match[1].length();
              parsed =
                parsed.substring(0, third + diff) +
                replace[2] +
                parsed.substring(third + match[2].length() + diff);
            }
          }
          break;
        case END :
          if(parsed.lastIndexOf(match[0]) == parsed.length() - match[0].length()){
            parsed =
              replace[0] +
              parsed.substring(0, parsed.length() - match[0].length()) +
              replace[1];
          }
          break;
        case FAIL :
        default :
          Parser.error("no valid mode set");
          break;
      }
      return parsed;
    }
  }

  private static String bottom = null;
  private static String configuration = null;
  private static String top = null;
  private static ArrayList<Config> settings = null;

  /**
   * main()
   *
   * The main entry point into the program.
   *
   * @param args The arguments into the program.
   **/
  public static void main(String[] args){
    for(int x = 0; x < args.length; x++){
      switch(args[x]){
        case "-a" :
        case "--about" :
          x = about(args, x);
          break;
        case "-b" :
        case "--bottom" :
          x = bottom(args, x);
          break;
        case "-c" :
        case "--config" :
          x = config(args, x);
          break;
        case "-h" :
        case "--help" :
          x = help(args, x);
          break;
        case "-t" :
        case "--top" :
          x = top(args, x);
          break;
        case "-v" :
        case "--version" :
          x = version(args, x);
          break;
        default :
          /* Check for config file and build it */
          if(configuration == null){
            Parser.error("cannot process without parser configuration");
          }else{
            /* Setup settings */
            settings = new ArrayList<Config>();
            /* Read settings file and add to settings array */
            try(BufferedReader br = new BufferedReader(new FileReader(configuration))){
              String line;
              while((line = br.readLine()) != null){
                settings.add(new Config(line));
              }
            }catch(IOException e){
              Parser.error("failed to read config file");
            }
          }
          /* Process files */
          for(; x < args.length; x++){
            /* Open file for processing */
            try(BufferedReader br = new BufferedReader(new FileReader(args[x]))){
              /* Open output file for processing */
              String outFile = args[x].substring(0, args[x].indexOf(".")) + ".html";
              BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
              /* Add the top if it exists */
              if(top != null){
                bw.write(top);
              }
              /* Read and parse the lines */
              String line;
              while((line = br.readLine()) != null){
                /* Iterate over our line parsers */
                for(int z = 0; z < settings.size(); z++){
                  line = settings.get(z).parse(line);
                }
                bw.write(line);
              }
              /* Add the bottom if it exists */
              if(bottom != null){
                bw.write(bottom);
              }
              /* Close the output stream */
              bw.close();
            }catch(IOException e){
              Parser.error("failed to read input file");
            }
          }
          break;
      }
    }
  }

  /**
   * about()
   *
   * Display information about the program.
   *
   * @param args The arguments to be parsed.
   * @param offset The current offset in the arguments.
   * @return The new offset relative to the current offset.
   **/
  public static int about(String[] args, int offset){
    System.out.println("Written by Netizens 2017");
    return offset;
  }

  /**
   * bottom()
   *
   * Bottom fed file into the output.
   *
   * @param args The arguments to be parsed.
   * @param offset The current offset in the arguments.
   * @return The new offset relative to the current offset.
   **/
  public static int bottom(String[] args, int offset){
    bottom = "";
    try(BufferedReader br = new BufferedReader(new FileReader(args[++offset]))){
      String line;
      while((line = br.readLine()) != null){
        bottom += line;
      }
    }catch(IOException e){
      Parser.error("failed to read bottom file");
    }
    return offset;
  }

  /**
   * config()
   *
   * Accept a config file.
   *
   * @param args The arguments to be parsed.
   * @param offset The current offset in the arguments.
   * @return The new offset relative to the current offset.
   **/
  public static int config(String[] args, int offset){
    configuration = args[++offset];
    return offset;
  }

  /**
   * help()
   *
   * Display program help information.
   *
   * @param args The arguments to be parsed.
   * @param offset The current offset in the arguments.
   * @return The new offset relative to the current offset.
   **/
  public static int help(String[] args, int offset){
    System.out.println(
      "\nParser [OPT(S)] [FILE(S)]" +
      "\n" +
      "\n  OPTionS" +
      "\n" +
      "\n    -a  --about    About this program" +
      "\n    -b  --bottom   Bottom HTML file" +
      "\n                     <STR> Bottom file" +
      "\n    -c  --config   Parser configuration" +
      "\n                     <STR> Configuration filename" +
      "\n    -h  --help     Display this help" +
      "\n    -t  --top      Top HTML file" +
      "\n                     <STR> Top file" +
      "\n    -v  --version  The program version" +
      "\n" +
      "\n  FILES" +
      "\n" +
      "\n    The markdown files to be parsed." +
      "\n"
    );
    return offset;
  }

  /**
   * top()
   *
   * Top fed file into the output.
   *
   * @param args The arguments to be parsed.
   * @param offset The current offset in the arguments.
   * @return The new offset relative to the current offset.
   **/
  public static int top(String[] args, int offset){
    top = "";
    try(BufferedReader br = new BufferedReader(new FileReader(args[++offset]))){
      String line;
      while((line = br.readLine()) != null){
        top += line;
      }
    }catch(IOException e){
      Parser.error("failed to read top file");
    }
    return offset;
  }

  /**
   * version()
   *
   * Display program version.
   *
   * @param args The arguments to be parsed.
   * @param offset The current offset in the arguments.
   * @return The new offset relative to the current offset.
   **/
  public static int version(String[] args, int offset){
    System.out.println("0.0.1");
    return offset;
  }

  /**
   * error()
   *
   * Display the error and quit.
   *
   * @param msg The error message to be displayed.
   **/
  public static void error(String msg){
    System.err.println("[ERR] " + msg);
    System.exit(0);
  }
}
