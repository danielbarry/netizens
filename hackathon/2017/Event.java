import java.net.Socket;

/**
 * Hackathon Event
 *
 * Capture the flag event for those interested in joining our hacking
 * team and anybody interested in difficult challenges in general.
 * The key is to "have fun".
 *
 * @author Netizens
 * @version 1300 on 15/03/2017 in LB154
 **/
public class Event{
  private static String img = "104.20.208.21",
    hlp = "\n\nEvent [KEY]\n  KEY: The password.",
    lut = "0123456789abcdef";
  private static int len = 16;

  public static void main(String[] args) throws Exception{
    if(args.length <= 0){ System.out.println(new String(logo())+hlp); }
    else{ test(args, logo()); }
  }

  private static byte[] logo() throws Exception{
    byte[] data = new byte[8192];
    Socket s = new Socket(img, 80);
    s.getOutputStream().write((
      "GET /raw/XumidyfE HTTP/1.1\r\nAccept: */*\r\nHost: pasteb" +
      "in.com\r\nConnection: keep-alive\r\n\r\n"
    ).getBytes()); // Make a valid request
    Thread.sleep(500); // Fill the network buffer
    s.getInputStream().read(data); // Read the response
    return data;
  }

  private static void test(String[] args, byte[] data) throws Exception{
    if(args.length >= 2){
      String s = ((new String(data)).substring(
        (new String(data).lastIndexOf("10dd")),
        (new String(data)).lastIndexOf("0")
      )).replace(" ", "").replace("\n", "");
      String p = args[0] + args[1] + args[1] + args[0] + args[1];
      String h = "";
      for(int x = 0; x < len; x++)
        h += lut.charAt(
          ((int)(p.charAt(x)) + (int)(s.charAt(x * 2))) % lut.length()
        );
      System.out.println("FLAG:{" + h + "}");
    }else{ System.out.println("Invalid password."); }
  }
}
