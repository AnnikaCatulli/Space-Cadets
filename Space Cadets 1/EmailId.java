
//The purpose of this program is to take the email id of someone at the university of Southampton then return their full name.
import java.io.*;
import java.net.*;

public class EmailId {
	public static void main(String[] args )throws Exception {
		
		//using a buffered reader to get an input from command line.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String name = in.readLine();
		
		//forming the address
		String address = "https://www.ecs.soton.ac.uk/people/" + name;
		//setting up a new buffered reader to read from the web page.
		URL page = new URL(address);
		BufferedReader new_in = new BufferedReader( new InputStreamReader(page.openStream()));
		
		String inputLine;
	
        do { 
		inputLine = new_in.readLine(); 
		}
		while (inputLine != null &&  !inputLine.contains("og:title"));//terminates when the correct line is reached or there are no more lines.
		
		int ind = inputLine.indexOf("content=");
		int ind2 = inputLine.indexOf(" />");
		System.out.println(inputLine.substring(ind + 9 , ind2 - 1));//this is the index of the c of content+9. Then the end of the string.
		
		new_in.close();//closes web reader 
		in.close();//closes terminal reader.
		
	}
	
}