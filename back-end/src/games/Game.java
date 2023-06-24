package games;

public class Game {
	
	public enum GAMES {
		MINECRAFT, VAMPIRE_SURVIVORS, RESIDENT_EVIL_4
	}
		
	protected int id;
	protected String name;
	protected String description;
	
	public Game(GAMES game) {
		switch(game) {
			case MINECRAFT:
				minecraft();
				break;
			case RESIDENT_EVIL_4:
				residentEvil4();
				break;
			case VAMPIRE_SURVIVORS:
				vampireSurvivors();
				break;
		}
	}
	
	private void minecraft() {
		this.id = 1;
		this.name = "Minecraft";
		this.description = "Jogo quadrado de sobrevivência.";
	}
	
	private void vampireSurvivors() {
		this.id = 2;
		this.name = "Vampire Survivors";
		this.description = "Sobrevive ai pae";
	}
	
	private void residentEvil4() {
		this.id = 3;
		this.name = "Resident Evil 4";
		this.description = "Resgata a chata da Ashley aí";
	}
	
}