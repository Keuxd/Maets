package config;

import java.io.IOException;
import java.nio.channels.FileChannel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import games.GamesUtil;

public class OnlineConfigs {
	
	private static FileChannel onlineConfigFile;
	
	public static void initialize(String folderPath) throws IOException {
		if(onlineConfigFile != null) {
			System.out.println("OnlineConfigs already initialized, ignoring call.");
			return;
		}
		
		onlineConfigFile = ConfigUtils.initializeFile(folderPath, "OnlineConfigs.ini");
		
		// File is empty (Just got created)
		if(onlineConfigFile.size() == 0) {
			writeDefaultInfoInConfigFile();
		}

	}
	
	public static void addGameToLibrary(int gameId) throws IOException {
		JsonObject json = ConfigUtils.getContentInChannelAsJson(onlineConfigFile);
		
		JsonObject game = new JsonObject();
		game.addProperty("playTime", "0");
		game.addProperty("lastSession", "");
		
		json.getAsJsonObject("gamesInLibrary").add(Integer.toString(gameId), game);
		
		System.out.println("\"" + GamesUtil.getGameFromId(gameId).getName() + "\" added to library.");
		ConfigUtils.writeToFileChannel(new Gson().toJson(json), onlineConfigFile);
	}
	
	public static boolean isInLibrary(String gameId) throws IOException {
		JsonObject gamesInLibrary = ConfigUtils.getContentInChannelAsJson(onlineConfigFile).getAsJsonObject("gamesInLibrary");
		JsonElement game = gamesInLibrary.get(gameId);
		
		if(game == null)
			return false;
		
		return true;
	}
	
	public static JsonArray addIsInLibraryToGamesJsonArray(JsonArray games) throws IOException {
		JsonObject gamesInLibrary = ConfigUtils.getContentInChannelAsJson(onlineConfigFile).getAsJsonObject("gamesInLibrary");
		
		for(int i = 0; i < games.size(); i++) {
			JsonObject game = games.get(i).getAsJsonObject();
			String gameId = game.get("id").getAsString();
			
			if(gamesInLibrary.get(gameId) == null) {
				game.addProperty("isInLibrary", false);
			} else {
				game.addProperty("isInLibrary", true);
			}
		}
		
		return games;
	}
	
	private static void writeDefaultInfoInConfigFile() throws IOException {
		JsonObject json = new JsonObject();
		json.addProperty("type", "OnlineConfigs");
		json.add("gamesInLibrary", new JsonObject());		
		
		String defaultInfo = new Gson().toJson(json);
		
		ConfigUtils.writeToFileChannel(defaultInfo, onlineConfigFile);
	}
	
}