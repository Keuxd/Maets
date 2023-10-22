package lan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequest {
	
	private String dataToSend;
	private String dataReceived;
	private int responseCode;
	
	private URL url;
	private ArrayList<String> propertyKeys;
	private ArrayList<String> propertyValues;
	
	public HttpRequest(URL url) {
		this.url = url;
		this.propertyKeys = new ArrayList<>();
		this.propertyValues = new ArrayList<>();
	}
	
	public void GET() throws IOException {
		HttpURLConnection connection = request("GET", false);
		
		generateDataReceived(connection.getInputStream());

		this.responseCode = connection.getResponseCode();
		
		connection.disconnect();
	}
	
	public void POST() throws IOException {
		HttpURLConnection connection = request("POST", true);
		
		sendData(connection.getOutputStream());
		
		generateDataReceived(connection.getInputStream());
		
		this.responseCode = connection.getResponseCode();
		
		connection.disconnect();
	}
	
	public void DELETE() throws IOException {
		HttpURLConnection connection = request("DELETE", false);
		
		this.responseCode = connection.getResponseCode();
		
		connection.disconnect();
	}
	
	private HttpURLConnection request(String requestMethod, boolean willSendData) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(requestMethod);
		connection.setDoOutput(willSendData);
		
		for(int i = 0; i < propertyKeys.size(); i++) {
			connection.setRequestProperty(propertyKeys.get(i), propertyValues.get(i));
		}
		
		return connection;
	}
	
	private void generateDataReceived(InputStream connectionInputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connectionInputStream));
		
		String inputLine;
		StringBuilder response = new StringBuilder();
		
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            response.append(System.lineSeparator());
        }
		
		in.close();
		
		this.dataReceived = response.toString();
	}
	
	private void sendData(OutputStream connectionOutputStream) throws IOException {
		byte[] input = dataToSend.getBytes("UTF-8");
		connectionOutputStream.write(input, 0, input.length);
	}
	
	public void addRequestProperty(String key, String value) {
		propertyKeys.add(key);
		propertyValues.add(value);
	}
	
	public void setDataToSend(String dataToSend) {
		this.dataToSend = dataToSend;
	}
	
	public int getResponseCode() {
		return this.responseCode;
	}
	
	public String getDataReceived() {
		return this.dataReceived;
	}
	
}
