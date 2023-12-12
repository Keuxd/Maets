const { app, dialog } = require("electron");
const { WindowsNode } = require("./WindowsNode");

class UtilNode {
	
	static fatalErrorPopup(title, content) {
		if(WindowsNode.MAIN_WINDOW != null)	
			WindowsNode.MAIN_WINDOW.hide();
		
		dialog.showErrorBox(title, content);
		app.quit();
	}
	
	static sendToWebContext(message) {
		WindowsNode.MAIN_WINDOW.webContents.send("node-message", message);
	}
	
	// Legacy
	static isJson(string) {
		try {
			return [true, JSON.parse(string)];
		} catch(exception) {
			return [false, null];
		}
	}
	
}

module.exports = { UtilNode };