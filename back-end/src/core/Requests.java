package core;

public class Requests {
	
	public static void process(String request) {
		switch(request) {
			case "buttonPress": {
				butt();
				break;
			}
			
			case "Hello from Electron!": {
				Electron.response("Hello from java!");
				break;
			}
		}
	}
	
	private static void butt() {
		Electron.response("Button Press Received !!");
	}
}