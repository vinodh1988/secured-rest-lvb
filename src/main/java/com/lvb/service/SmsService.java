package com.lvb.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SmsService {
	public static String sendSms(String mobile, String otp) {
		try {
			// Construct data
			String apiKey = "apikey=" + "fZ1i0dax+yM-ZLp7RMuY4ATuDeeHRVRRFvdf63hZvx";
			String message = "&message=" + "Your verification otp is "+otp;
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + "91"+mobile;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			System.out.println("SMS Sent...!!!!");
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}

}
