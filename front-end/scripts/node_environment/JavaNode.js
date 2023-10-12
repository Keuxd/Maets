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
			
			const availableJson = UtilNode.isJson(response);
        	if(availableJson[0]) {
				WindowsNode.MAIN_WINDOW.webContents.send("java-backend-json", availableJson[1]);
			} else {
        		WindowsNode.MAIN_WINDOW.webContents.send('java-backend-response', response.split(' '));
			}
		})
	}	
}

module.exports = { JavaNode };