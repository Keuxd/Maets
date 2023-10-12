const { app, dialog } = require("electron");
const { WindowsNode } = require("./WindowsNode");

class UtilNode {
	
	static isJson(string) {
		try {
			return [true, JSON.parse(string)];
		} catch(exception) {
			return [false, null];
		}
	}
	
	static fatalErrorPopup(title, content) {
		WindowsNode.MAIN_WINDOW.hide();
		dialog.showErrorBox(title, content);
		app.quit();
	}
	
	
}

module.exports = { UtilNode };