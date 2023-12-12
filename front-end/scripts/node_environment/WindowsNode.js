const { BrowserWindow } = require("electron");

class WindowsNode {
	
	static MAIN_WINDOW;
	
	static createMainWindow() {
		WindowsNode.MAIN_WINDOW = new BrowserWindow({
			width: 1280, // 800 1280 1920
			height: 720, // 600 720 1080
			resizable: false,
			frame: false,
			webPreferences: {
				nodeIntegration: true,
				contextIsolation: false,
			},
		});
		
		WindowsNode.MAIN_WINDOW.loadFile("main.html");
	}
	
}

module.exports = { WindowsNode };