package Advocacy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class Test_URL_Req {
	
	public static void main(String[] args) {
		System.out
        .println(jsonGetRequest("https://benkrokower:gF6ExsBpXyATbNzpvTHx@api.browserstack.com/automate/sessions/fa113bded4d88093e1bcd8ec1ef2a44432d2eba3.json"));
  }
		   
	private static String streamToString(InputStream inputStream) {
	    String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
	    return text;
	  }

	  public static String jsonGetRequest(String urlQueryString) {
	    String json = null;
	    try {
	      URL url = new URL(urlQueryString);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      connection.setDoOutput(true);
	      connection.setInstanceFollowRedirects(false);
	      connection.setRequestMethod("GET");
	      connection.setRequestProperty("Content-Type", "application/json");
	      connection.setRequestProperty("charset", "utf-8");
	      connection.connect();
	      InputStream inStream = connection.getInputStream();
	      json = streamToString(inStream); // input stream to string
	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	    return json;
	  }
}
