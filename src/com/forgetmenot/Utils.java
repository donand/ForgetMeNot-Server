package com.forgetmenot;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;

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
		
	//calcola il livello di acqua e fertilizzante
	public static int calcolaLivello(Long ultimaData, int intervallo) {
		Date now = new Date(new java.util.Date().getTime());
		System.out.println("now: " + now.getTime() + "\nultima: " + ultimaData);
		
		double result = ((double) (now.getTime() - ultimaData))/ (1000 * 60 * 60 * 24);
		System.out.println("result1: " + result);
		//result è un double >= 0 e rappresenta il numero di giorni passati dall'ultima volta
		result = (result < intervallo)? result : intervallo;
		//ora ho result >= 0 && result <= intervallo
		result = (result/intervallo)*10;
		//ora result >= 0 && result <= 10
		result = 10 - result;
		System.out.println("result2: " + result);
		
		//result basso indica che deve essere innaffiata (o fertilizzata)
		//result alto indica che è stata innaffiata da poco
		return (int) Math.round(result);
	}
}
