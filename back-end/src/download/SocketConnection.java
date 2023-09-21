package download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SocketConnection {
	
	private SocketConnection() {}
	
	public static InputStream getURLSource(String link) throws IOException {
		return getURLSourceStream(new URL(link));
	}
	
    public static InputStream getURLSourceStream(URL urlObject) throws IOException {
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        return urlConnection.getInputStream();
    }
    
    public static String findLineInInputStreamWhichContains(InputStream is, String contains) throws IOException {
    	try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {    		
    		for(String inputLine; (inputLine = reader.readLine()) != null;) {
    			if(inputLine.contains(contains)) {
    				return inputLine;
    			}
    		}
    		return null;
    	}
    }
    
    public static String getAllLinesFromInputStream(InputStream is) throws IOException {
    	try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
    		StringBuilder sb = new StringBuilder();
    		for(String inputLine; (inputLine = reader.readLine()) != null;) {
    			sb.append(inputLine);
    		}
    		return sb.toString();
    	}
    }
    
	public static InputStream sendHttpPostRequest(String url, String[] params, String[] values, CookiePolicy cp) throws IOException {
		if(params.length != values.length)
			throw new IOException("Amount of parameters is different from the amount of values");
		
		String charset = StandardCharsets.UTF_8.name();
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < params.length; i++) {
			sb.append(params[i]);
			sb.append("=");
			sb.append(URLEncoder.encode(values[i], charset));
			sb.append("&");
		}
		sb.deleteCharAt(sb.length()-1);
		
		CookieHandler.setDefault(new CookieManager(null, cp));
		
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

		try(OutputStream output = connection.getOutputStream()) {
		    output.write(sb.toString().getBytes(charset));
		}
		
		return connection.getInputStream();
	}
	
	public static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
		    TrustManager[] trustAllCerts = new TrustManager[] {
		    	new X509TrustManager() {
		    		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		              return null;
		            }

		            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

		            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
		        }
		    };

		    // Install the all-trusting trust manager
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		    // Create all-trusting host name verifier
		    HostnameVerifier allHostsValid = new HostnameVerifier() {
		    	public boolean verify(String hostname, SSLSession session) {
		    		return true;
		        }
		    };

		    // Install the all-trusting host verifier
		    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		    
		} catch(NoSuchAlgorithmException e) {
		      e.printStackTrace();
		} catch (KeyManagementException e) {
		      e.printStackTrace();
		}
	}
}