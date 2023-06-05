package core;

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
			
			case "buttonPress": {
				butt();
				break;
			}
			
			case "Handshake from Electron!": {
				Electron.response("Handshake from Java!");
				break;
			}
		}
	}
	
	private static void butt() {
		Electron.response("Button Press Received !!");
	}
}