package com.broker.jastags.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

public class  TwitterUtils {
	
	public static final String TAG = "TwitterUtils";
	
	public static String appAuthentication(){

		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		BufferedReader bufferedReader = null;
		StringBuilder response = null;

		try {
			URL url = new URL(ConstantsUtils.URL_AUTHENTICATION);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);

			String accessCredential = ConstantsUtils.CONSUMER_KEY + ":" + ConstantsUtils.CONSUMER_SECRET;
			String authorization = "Basic " + Base64.encodeToString(accessCredential.getBytes(), Base64.NO_WRAP);
			String param = "grant_type=client_credentials";

			httpConnection.addRequestProperty("Authorization", authorization);
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			httpConnection.connect();
			
			outputStream = httpConnection.getOutputStream();
			outputStream.write(param.getBytes());
			outputStream.flush();
			outputStream.close();

			bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String line;
			response = new StringBuilder();

			while ((line = bufferedReader.readLine()) != null){            
				response.append(line);	
			}

			Log.d(TAG, "POST response code: " + String.valueOf(httpConnection.getResponseCode()));
			Log.d(TAG, "JSON response: " + response.toString());

		} catch (Exception e) {
			Log.e(TAG, "POST error: " + Log.getStackTraceString(e));
			
		}finally{
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
		return response.toString();
	}
	
	public static String getTimelineForSearchTerm(String searchTerm){
		
		HttpURLConnection httpConnection = null;
		BufferedReader bufferedReader = null;
		StringBuilder response = new StringBuilder();

		try {
			URL url = new URL(ConstantsUtils.URL_SEARCH + searchTerm + "&count=20");
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");

			String jsonString = appAuthentication();
			JSONObject jsonObjectDocument = new JSONObject(jsonString);
			String token = jsonObjectDocument.getString("token_type") + " " + 
					jsonObjectDocument.getString("access_token");

			httpConnection.setRequestProperty("Authorization", token);
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.connect();

			bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

			String line;
			while ((line = bufferedReader.readLine()) != null){            
				response.append(line);	
			}
			
			Log.d(TAG, "GET response code: " + String.valueOf(httpConnection.getResponseCode()));
			Log.d(TAG, "JSON response: " + response.toString());
			
			return response.toString();

		} catch (Exception e) {
			Log.e(TAG, "GET error: " + Log.getStackTraceString(e));      
			return null;

		}finally {
			if(httpConnection != null){
				httpConnection.disconnect();
			}
		}
	}

}
