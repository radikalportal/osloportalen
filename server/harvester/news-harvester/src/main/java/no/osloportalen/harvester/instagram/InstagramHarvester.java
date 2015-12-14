package no.osloportalen.harvester.instagram;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpRequest;


public class InstagramHarvester {
	private final static String INSTAGRAM_CLIENT_ID = "e2e9c1310aea454a847c4643cd88836e";
	
	public static void  main (String[] args) throws Exception {
		
		String authorizationURL = "https://api.instagram.com/oauth/authorize/?client_id=" + INSTAGRAM_CLIENT_ID + "&redirect_uri=REDIRECT-URI&response_type=code";
		URLConnection connection = new URL(authorizationURL).openConnection();
		
		InputStream inputStream = connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream);
		StringWriter writer = new StringWriter();

//		IOUtils.copy(inputStream, writer, "UTF-8");
		
		
		if ( reader.ready() ) {
//			System.out.println("Data: " + reader.re);
		}
		
		System.out.println("Authorization url: " + authorizationURL);
		
		HttpRequest request = new BasicHttpRequest("GET", authorizationURL);
		Header allHeaders[] = request.getAllHeaders();
		
		for ( Header currentHeader : allHeaders ) {
			System.out.println("Current header = " + currentHeader.toString());
		}
		
		
	}
	
}
