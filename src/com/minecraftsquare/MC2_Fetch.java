package com.minecraftsquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MC2_Fetch {
	
	public String value;
	public JSONObject obj;
	
	public MC2_Fetch(String URLString) {
		// TODO Auto-generated constructor stub
		
		try {
			URL mcs = new URL(URLString);
			InputStream is = mcs.openStream();
			InputStreamReader is2 = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(is2);

			String read = br.readLine();
			while( read != null ) {
				sb.append( read );
				read = br.readLine();
			}

			// at this point, string checks out
			this.value = sb.toString();
			JSONParser data = new JSONParser();
			this.obj = (JSONObject) data.parse( this.value );
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
