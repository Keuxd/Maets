package games;

public class Game {
	
	public enum GAMES {
		STARDEW_VALLEY, VAMPIRE_SURVIVORS, BLOONS_TD6
	}
		
	protected int id;
	protected String name;
	protected String description;
	
	public Game(GAMES game) {
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
	
	public static Game[] getAllGames() {
		GAMES[] gamesEnum = GAMES.values();
		Game[] gamesArray = new Game[gamesEnum.length];
		
		for(int i = 0; i < gamesEnum.length; i++) {
			gamesArray[i] = new Game(gamesEnum[i]);
		}
		
		return gamesArray;
	}
	
}