package core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

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
				int code = Mega.run(CommandsEnum.VERSION);
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
				JsonArray gamesArray = new JsonArray();
				gamesArray.add("Shop");
				gamesArray.addAll(JsonParser.parseString(new Gson().toJson(Game.getAllGames())).getAsJsonArray());
				Electron.response(gamesArray.toString());
				break;
			}
		}
	}
}