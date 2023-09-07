package games;

public class Game {
	
	public enum Games {
		SECRETS_OF_GRINDEA, VAMPIRE_SURVIVORS
	}
		
	private int id;
	private String name;
	private String description;
	
	public Game(Games game) {
		switch(game) {
			case SECRETS_OF_GRINDEA:
				secretsOfGrindea();
				break;
			case VAMPIRE_SURVIVORS:
				vampireSurvivors();
				break;
		}
	}
	
	private void secretsOfGrindea() {
		this.id = 0;
		this.name = "Secrets Of Grindea";
		this.description = "Secrets of Grindea is an old-school Action RPG with co-op support for up to 4 players.\nIt is a tribute to and sometimes a parody of the old SNES games so many of us grew up with and loved!\nJourney through fantastical lands and battle tons of different enemies and bosses in your quest for truth, friendship and, above all, finding the world's rarest treasures!";
	}
	
	private void vampireSurvivors() {
		this.id = 1;
		this.name = "Vampire Survivors";
		this.description = "VS DESCRIPTION PLACEHOLDER";
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static Game[] getAllGames() {
		Games[] gamesEnum = Games.values();
		Game[] gamesArray = new Game[gamesEnum.length];
		
		for(int i = 0; i < gamesEnum.length; i++) {
			gamesArray[i] = new Game(gamesEnum[i]);
		}
		
		return gamesArray;
	}
	
	public static Game getGameFromId(int id) {
		Game[] games = getAllGames();
	
		for(int i = 0; i < games.length; i++) {
			if(games[i].id == id) {
				return games[i];
			}
		}
		
		return null;
	}
}