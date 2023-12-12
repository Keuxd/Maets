package lan;

import java.io.IOException;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ZeroTierAPI {

	private static String API = "https://api.zerotier.com/api/v1";
	public static String SECRET_VALUE = "";
	
	private static HttpRequest generateDefaultRequest(String endPoint) throws IOException {
		URL url = new URL(API + endPoint);
		HttpRequest request = new HttpRequest(url);
		request.addRequestProperty("Authorization", "Bearer " + SECRET_VALUE);
		return request;
	}
	
	private static JsonObject generateDefaultJson(int responseCode, String responseData) throws IOException {
		if(responseData == null) responseData = "null";
		
		JsonObject json = new JsonObject();
		json.addProperty("responseCode", responseCode);
		json.add("data", JsonParser.parseString(responseData));
		return json;
	}
	
	public static JsonObject createNetwork() throws IOException {
		HttpRequest request = generateDefaultRequest("/network");
		
		request.addRequestProperty("Content-Type", "application/json");
		request.setDataToSend("{ }");
		request.POST();
		
		return generateDefaultJson(request.getResponseCode(), request.getDataReceived());
	}
	
	public static JsonObject deleteNetwork(String networkId) throws IOException {
		HttpRequest request = generateDefaultRequest("/network/" + networkId);
		request.DELETE();
		
		return generateDefaultJson(request.getResponseCode(), request.getDataReceived());
	}
	
	public static JsonObject getMembersOnNetwork(String networkId) throws IOException {
		HttpRequest request = generateDefaultRequest("/network/" + networkId + "/member");
		request.GET();
		
		return generateDefaultJson(request.getResponseCode(), request.getDataReceived());
	}
	
	public static JsonObject deleteNetworkMember(String networkId, String memberId) throws IOException {
		HttpRequest request = generateDefaultRequest("/network/" + networkId + "/member/" + memberId);
		request.DELETE();
		
		return generateDefaultJson(request.getResponseCode(), request.getDataReceived());
	}
	

}