const { WindowsNode } = require("./WindowsNode"); 
const { UtilNode } = require("./UtilNode");
const net = require("net");

class JavaNode {
	
	static NUMBER_PORT = 1234;
	static CLIENT;
	
	static connectClientSocket() {
		const client = new net.Socket();

		client.connect(JavaNode.NUMBER_PORT, "localhost", () => {
			console.log("Connected to java server");
		})
		
		client.on("close", () => {
			UtilNode.fatalErrorPopup("Server Error", "Server was closed, closing application..");
		})
		
		client.on("error", (error) => {
			console.log("Error in electron: " + error);
		})

		JavaNode.CLIENT = client;
	}
	
	// ipcMain CAN'T BE REQUIRED HERE
	static setupListeners(ipcMain) {
		// Sending messages from webcontext into server
		ipcMain.on("send-to-backend", (event, message) => {
			console.log("- Sent: " + message);
			JavaNode.CLIENT.write(message + "\r");
		})
		
		// Receiving messages from server and sending to webcontext
		JavaNode.CLIENT.on("data", (data) => {
			const response = data.toString().trim();
			console.log("+ Received: " + response);
			
			JavaNode.processResponse(JSON.parse(response));
			//WindowsNode.MAIN_WINDOW.webContents.send("java-backend-response", response.split(" "));
		})
	}
	
	static sendMessageToJava(message) {
		console.log("- Sent: " + message);
		JavaNode.CLIENT.write(message + "\r");
	}
	
	static processResponse(response) {
		switch(response.type) {
			case "mega" : {
				switch(response.response) {
					case -1: UtilNode.fatalErrorPopup("Error", "Java Console Error.."); break;
					case 2: UtilNode.fatalErrorPopup("Error", "MegaCMD Not Found.."); break;
					default: JavaNode.sendMessageToJava("isLoggedIn"); break;
				}
				break;
			}
			
			case "isLoggedIn" : {
				if(response.response) {
					UtilNode.sendToWebContext("invokeLoginScreen");
				} else {
					UtilNode.sendToWebContext("invokeLoginScreen");
				}
				break;
			}
		}
	}
}

module.exports = { JavaNode };