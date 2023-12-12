package core;

import com.google.gson.JsonObject;

public class JsonMaetsResponse {
	
	private JsonObject json;
	
	public JsonMaetsResponse(String type, String response) {
		init(type);
		json.addProperty("response", response);
	}
	
	public JsonMaetsResponse(String type, Number response) {
		init(type);
		json.addProperty("response", response);
	}
	
	public JsonMaetsResponse(String type, boolean response) {
		init(type);
		json.addProperty("response", response);
	}
	
	private void init(String type) {
		json = new JsonObject();
		json.addProperty("type", type);
	}
	
	@Override
	public String toString() {
		return json.toString();
	}
	
}
