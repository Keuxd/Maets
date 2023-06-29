package games;

public class Game {
	
	public enum Games {
		STARDEW_VALLEY, VAMPIRE_SURVIVORS, BLOONS_TD6, MINECRAFT, HADES, GRAND_THEFT_AUTO_5
	}
		
	private int id;
	private String name;
	private String description;
	
	public Game(Games game) {
		switch(game) {
			case STARDEW_VALLEY:
				stardewValley();
				break;
			case VAMPIRE_SURVIVORS:
				vampireSurvivors();
				break;
			case BLOONS_TD6:
				bloonsTd6();
				break;
			case GRAND_THEFT_AUTO_5:
				grandTheftAuto5();
				break;
			case HADES:
				hades();
				break;
			case MINECRAFT:
				minecraft();
				break;
		}
	}
	
	private void stardewValley() {
		this.id = 1;
		this.name = "Stardew Valley";
		this.description = "You've inherited your grandfather's old farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin your new life. Can you learn to live off the land and turn these overgrown fields into a thriving home?";
	}
	
	private void vampireSurvivors() {
		this.id = 2;
		this.name = "Vampire Survivors";
		this.description = "Sobrevive ai pae";
	}

	private void bloonsTd6() {
		this.id = 3;
		this.name = "Bloons TD6";
		this.description = "Mamacos";
	}
	
	private void minecraft() {
		this.id = 4;
		this.name = "Minecraft";
		this.description = "Jogo quadrado goes brrrrrrrr";
	}
	
	private void hades() {
		this.id = 5;
		this.name = "Hades";
		this.description = "Tenho que zerar ainda";
	}
	
	private void grandTheftAuto5() {
		this.id = 6;
		this.name = "Grand Theft Auto 5";
		this.description = "GTA";
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