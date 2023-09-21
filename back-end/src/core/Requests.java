package core;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import config.OnlineConfigs;
import games.Game;
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
				Electron.response("isLoggedIn " + (code == 0));
				break;
			}
			
			case "mega" : {
				int code;
				if(Mega.isInstalled()) {
					code = Mega.run(CommandsEnum.VERSION);
				} else {
					code = 2;
				}
				Electron.response("mega " + code);
				break;
			}
			
			case "firstName" : {
				int code = Mega.run(CommandsEnum.USERATTR, "firstname");
				String firstName = Mega.lastOutput.split(" ")[3];
				Electron.response("firstName " + firstName);
				break;
			}
			
			case "lastName" : {
				int code = Mega.run(CommandsEnum.USERATTR, "lastname");
				String lastName = Mega.lastOutput.split(" ")[3];
				Electron.response("lastName " + lastName);
				break;
			}
			
			case "shop" : {
				JsonObject json = new JsonObject();
				try {
					json.addProperty("type", "Shop");
					JsonArray games = JsonParser.parseString(new Gson().toJson(Game.getAllGames())).getAsJsonArray();
					games = OnlineConfigs.addIsInLibraryToGamesJsonArray(games);

					json.add("games", games);
				} catch(IOException e) {
					e.printStackTrace();
					json.addProperty("type", "Error");
				}
				
				Electron.response(json.toString());
				break;
			}
			
			case "download" : {
				
				try {
					
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