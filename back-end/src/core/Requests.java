package core;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import config.LocalConfigs;
import config.OnlineConfigs;
import games.AbstractGame;
import games.GamesUtil;
import mega.CommandsEnum;
import mega.Mega;

public class Requests {
	
	public static void process(String request) {
		String[] requestSplit = request.split(" ");
		
		switch(requestSplit[0]) {
			case "login" : {
				int code = Mega.run(CommandsEnum.LOGIN, requestSplit[1], requestSplit[2]);
				Electron.response("login " + code);
				break;
			}
			
			case "logout" : {
				int code = Mega.run(CommandsEnum.LOGOUT);
				Electron.response("logout " + code);
				break;
			}
			
			case "isLoggedIn": {
				int code = Mega.run(CommandsEnum.USERS);
				
				JsonMaetsResponse json = new JsonMaetsResponse("isLoggedIn", (code == 0));
				Electron.response(json.toString());
				break;
			}
			
			case "mega" : {
				int code;
				if(Mega.isInstalled()) {
					code = Mega.run(CommandsEnum.VERSION);
				} else {
					code = 2;
				}

				JsonMaetsResponse json = new JsonMaetsResponse("mega", code);
				Electron.response(json.toString());
				break;
			}
			
			case "firstName" : {
				Mega.run(CommandsEnum.USERATTR, "firstname");
				String firstName = Mega.lastOutput.split(" ")[3];

				JsonMaetsResponse json = new JsonMaetsResponse("firstName", firstName);
				Electron.response(json.toString());
				break;
			}
			
			case "lastName" : {
				Mega.run(CommandsEnum.USERATTR, "lastname");
				String lastName = Mega.lastOutput.split(" ")[3];
				
				JsonMaetsResponse json = new JsonMaetsResponse("lastName", lastName);
				Electron.response(json.toString());
				break;
			}
			
			case "shop" : {
				JsonObject json = new JsonObject();
				try {
					json.addProperty("type", "Shop");
					json.add("games", OnlineConfigs.addIsInLibraryToGamesJsonArray(LocalConfigs.addGameStateToJsonArray(GamesUtil.getAllGamesInJsonArray())));								
				} catch(IOException e) {
					e.printStackTrace();
					json.addProperty("type", "Error");
				}
				
				Electron.response(json.toString());
				break;
			}
			
			case "availability" : {
				JsonObject json = new JsonObject();
				try {
					json.addProperty("type", "Availability");
					AbstractGame game = GamesUtil.getGameFromId(Integer.parseInt(requestSplit[1]));
					boolean[] availableDownloads = game.getAvailableDownloads();
					
					JsonArray servers = new JsonArray(availableDownloads.length);
					servers.add(availableDownloads[0]);
					servers.add(availableDownloads[1]);
					json.add("servers", servers);
				} catch(Exception e) {
					e.printStackTrace();
					json.addProperty("type", "Error");
				}
				
				Electron.response(json.toString());
				break;
			}
			
			case "download" : {				
				try {
					AbstractGame game = GamesUtil.getGameFromId(Integer.parseInt(requestSplit[1]));
					boolean isAvailable = game.isDownloadAvailable(0);
					
					Electron.response("download " + requestSplit[1] + " " + isAvailable);
					
					
				} catch(Exception e) {
					e.printStackTrace();
					Electron.response("");
				}
				
				break;
			}
			
			case "install" : {
				
				break;
			}
			
			case "play" : {
				
				break;
			}
			
			case "isInLibrary" : {
				try {
					boolean answer = OnlineConfigs.isInLibrary(requestSplit[1]);
					Electron.response("isInLibrary " + requestSplit[1] + " " + answer);
				} catch(IOException e) {
					e.printStackTrace();
					JsonObject json = new JsonObject();
					json.addProperty("type", "Error");
					Electron.response(json.toString());
				}
				break;
			}
			
			case "addToLibrary" : {
				try {
					int gameId = Integer.parseInt(requestSplit[1]);
					OnlineConfigs.addGameToLibrary(gameId);
					Electron.response("addToLibrary " + 0);
				} catch(IOException e) {
					e.printStackTrace();
					Electron.response("addToLibrary " + 2);
				} catch(NumberFormatException e) {
					e.printStackTrace();
					Electron.response("addtoLibrary " + 9);
				}
				break;
			}
		}
	}
}