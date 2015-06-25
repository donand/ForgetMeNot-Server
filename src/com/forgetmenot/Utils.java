package com.forgetmenot;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
	
	//restituisce il JSONObject presente nel corpo della POST
	public static JSONObject parseJSONObject(HttpServletRequest request) throws IOException, JSONException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
			String line;
		    while ((line = reader.readLine()) != null) {
		    	sb.append(line).append('\n');
		    }
		} finally {
			reader.close();
		}
		return new JSONObject(sb.toString());
	}
	
	//restituisce il JSONArray presente nel corpo della POST
		public static JSONArray parseJSONArray(HttpServletRequest request) throws IOException, JSONException {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			try {
				String line;
			    while ((line = reader.readLine()) != null) {
			    	sb.append(line).append('\n');
			    }
			} finally {
				reader.close();
			}
			return new JSONArray(sb.toString());
		}
}
