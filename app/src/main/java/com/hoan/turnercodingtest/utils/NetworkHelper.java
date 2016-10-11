package com.hoan.turnercodingtest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkHelper {
	public interface ServerResponseListener {
		void onCompleted(String response);
		void onError(String error);
	}

	private static final int ONE_SECOND = 1000;
	private static final int BUFFER_SIZE = 8192;

	private NetworkHelper() {

	}
	
	public static void queryServer(String url, ServerResponseListener serverResponseListener) {
		HttpURLConnection serverConnection = getConnection(url);
		if (serverConnection != null) {
			fetch(serverConnection, serverResponseListener);
			serverConnection.disconnect();
		}
	}

	private static HttpURLConnection getConnection(String url) {
		HttpURLConnection serverConnection = null;
		try {
			final URL serverUrl = new URL(url);
			serverConnection = (HttpURLConnection) serverUrl.openConnection();
			serverConnection.setConnectTimeout(10 * ONE_SECOND);
			serverConnection.setReadTimeout(30 * ONE_SECOND);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return serverConnection;
	}
	
	private static void fetch(HttpURLConnection serverConnection, ServerResponseListener serverResponseListener) {
		String output = "";
		String line;
		BufferedReader in = null;
		try {
			InputStream inputStream = serverConnection.getInputStream();
			if (inputStream != null) {
				in = new BufferedReader(new InputStreamReader(inputStream), BUFFER_SIZE);

				while ((line = in.readLine()) != null) {
					output = output + line + "\n";
				}
				serverResponseListener.onCompleted(output);
			}
		} catch (IOException e) {
			e.printStackTrace();
			in = new BufferedReader(new InputStreamReader(serverConnection.getErrorStream()), BUFFER_SIZE);
			try {
				while ((line = in.readLine()) != null) {
					output = output + line + "\n";
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			serverResponseListener.onError(output);
		} finally {
			// Release resources
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ignore) {
				// what sensible thing can one do?
			}
		}
	}
}
