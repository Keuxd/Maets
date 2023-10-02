package games;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GamesUtil {
	
	private static AbstractGame[] getAllGames() {
		return new AbstractGame[] {
				new SoG(), new VS(), new DSCSCE()
		};
	}
	
	public static JsonArray getAllGamesInJsonArray() {
		JsonArray gamesArray = new JsonArray();
		
		for(AbstractGame game : getAllGames()) {
			JsonObject gameObject = new JsonObject();
			gameObject.addProperty("id", game.getId());
			gameObject.addProperty("name", game.getName());
			gameObject.addProperty("description", game.getDescription());
			gamesArray.add(gameObject);
		}
		
		return gamesArray;
	}
	
	public static AbstractGame getGameFromId(int id) {
		AbstractGame[] games = getAllGames();
		
		for(AbstractGame game : games) {
			if(game.getId() == id) {
				return game;				
			}
		}
		
		return null;
	}
}
