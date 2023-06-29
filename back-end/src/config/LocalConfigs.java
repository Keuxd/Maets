package config;

import java.io.IOException;
import java.nio.channels.FileChannel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LocalConfigs {
	
	private static FileChannel localConfigFile;
	
	public static void initialize(String folderPath) throws IOException {
		if(localConfigFile != null) {
			System.out.println("LocalConfigs already initialized, ignoring call.");
			return;
		}
		
		localConfigFile = ConfigUtils.initializeFile(folderPath, "LocalConfigs.ini");
		
		// File is empty (Just got created)
		if(localConfigFile.size() == 0) {
			writeDefaultInfoInConfigFile();
		}
	}
	
	private static void writeDefaultInfoInConfigFile() throws IOException {
		JsonObject json = new JsonObject();
		json.addProperty("type", "LocalConfigs");
		json.add("gamesStates", new JsonObject());
		
		String defaultInfo = new Gson().toJson(json);
		
		ConfigUtils.writeToFileChannel(defaultInfo, localConfigFile);
	}
	
	public static void changeGameState(String gameId, int gameState) throws IOException {
		JsonObject json = ConfigUtils.getContentInChannelAsJson(localConfigFile);
		JsonObject games = json.get("gamesStates").getAsJsonObject();
		
		// If the key already exists it'll be simply overwritten
		games.addProperty(gameId, gameState);
		
		String newContent = new Gson().toJson(json);
		
		ConfigUtils.writeToFileChannel(newContent, localConfigFile);
	}
	
	public static int getGameState(int gameId) throws IOException {
		JsonObject json = ConfigUtils.getContentInChannelAsJson(localConfigFile);
		JsonElement game = json.get("gamesStates").getAsJsonObject().get(Integer.toString(gameId));
		
		// Game is not in state, so it can be downloaded, code 0
		if(game == null) return 0;
		
		return game.getAsInt();
	}
	
	public static JsonObject getJson() throws IOException {
		return ConfigUtils.getContentInChannelAsJson(localConfigFile);
	}
	
}
