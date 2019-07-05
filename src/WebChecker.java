import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.net.*;
import java.io.*;

public class WebChecker extends Sendmail {
	
	public static String httpAnswer = null;

	public static void main(String[] args) throws IOException {

		//String url = "https://httpstat.us/400";
		String url = "https://index.hu/";
		int responseCode = 0;
		int timeout = 5000;
		boolean found = true;

		String from = "from address";
		String pass = "app password";
		String[] to = {"recipient1, recipient2"};
		String subject = "Subject Line";
		String body = null;

		// check if the keys can be found
		//String number = "302";

		HashMap<String, String> errorcodes = new HashMap<String, String>();
		readCSV(errorcodes);

		found = pingURL(url, timeout, responseCode, errorcodes); //method for checking connection, returns a boolean
		
		String response = pingURL2(url, timeout, responseCode, errorcodes);  // method for checking connection, returns a string
		System.out.println("the answer is: " + response);

		
		System.out.println();
		
		for (Entry<String, String> entry : errorcodes.entrySet()) {
			if (entry.getKey().contains(response)) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				body = "The status of this page " + "'" + url + "'" + " is: " + entry.getKey() + " : " + entry.getValue();
			}else{
				System.out.println("Error not found.");
			}
		}
		
		
		
		
		// connect to the website
		/*HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setConnectTimeout(timeout);
		connection.setReadTimeout(timeout);
		connection.setRequestMethod("HEAD");
		// get response from the website Head
		responseCode = connection.getResponseCode();
		System.out.println(responseCode);
		// convert the integer into string
		httpAnswer = Integer.toString(responseCode);
		System.out.println(httpAnswer);
		// check if the answer is among the error codes
		for (Entry<String, String> entry : errorcodes.entrySet()) {
			if (entry.getKey().contains(httpAnswer)) {
				// print out the status of the page
				System.out.println(entry.getKey() + " : " + entry.getValue());
				// create the body of the email message
				body = "The status of this page " + url + " is " + entry.getKey() + " : " + entry.getValue();
			}
		}
		if (200 <= responseCode && responseCode <= 399) {
			System.out.println("available");
		} else {
			System.out.println("Error occured, see the code: " + httpAnswer);
			found = false;
		}*/
		
		//body = "The status of this page " + url + " is " + entry.getKey() + " : " + entry.getValue();
		
		System.out.println(found);

		System.out.println(body);
		
		//if website is unavailable send email
		if (found != true) {
			sendFromGMail(from, pass, to, subject, body);
		} else {
			sendFromGMail(from, pass, to, subject, body);
			System.out.println("All good :)");
		}
	}

	public static boolean pingURL(String url, int timeout, int responseCode, HashMap<String, String> hash) {
		url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");
			responseCode = connection.getResponseCode();
			System.out.println(responseCode);
			httpAnswer = Integer.toString(responseCode);
			System.out.println(httpAnswer);
			for (Entry<String, String> entry : hash.entrySet()) {
				if (entry.getKey().contains(httpAnswer)) {
					System.out.println(entry.getKey() + " : " + entry.getValue());
				} else {
					System.out.println("Error not found");
				}
				return (200 <= responseCode && responseCode <= 399);
			}
		} catch (IOException exception) {
			return false;
		}
		return false;
	}

	public static String pingURL2(String url, int timeout, int responseCode, HashMap<String, String> hash) {
		url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("HEAD");
			responseCode = connection.getResponseCode();
			System.out.println(responseCode);
			httpAnswer = Integer.toString(responseCode);
			System.out.println(httpAnswer);
			for (Entry<String, String> entry : hash.entrySet()) {
				if (entry.getKey().contains(httpAnswer)) {
					System.out.println("The answer from the webpage is: " + entry.getKey() + " : " + entry.getValue());
				} else {
					System.out.println("Error not found");
				}
				
		}
			if (200 <= responseCode && responseCode <= 399) {
				System.out.println("available");
			} else {
				System.out.println("Error occured, see the code: " + httpAnswer);

		}} catch (IOException exception) {

		}
		return httpAnswer;
	}

	public static void readCSV(HashMap<String, String> hash) throws IOException {
		
		//read the csv file
		// D:\\Users\\AValdinger\\Documents\\
		BufferedReader br = new BufferedReader(new FileReader("http-status-codes-1.csv"));
		String line = null;
		System.out.println(br);
		
		//iterate trhough the csv file and map it to the hashmap
		while ((line = br.readLine()) != null) {
			String str[] = line.split(",");
			for (int i = 0; i < str.length; i++) {
				hash.put(str[0], str[1]);
			}

		}

		System.out.println(hash);

	}
}
