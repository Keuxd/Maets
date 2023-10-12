const { app, ipcMain, dialog } = require('electron');
const { JavaNode } = require("./JavaNode");
const { WindowsNode } = require("./WindowsNode");
const { UtilNode } = require("./UtilNode");

app.whenReady().then(() => {

	if(!isJava()) {
		UtilNode.fatalErrorPopup("Executable error", "Please open using Maets.bat");
		return;
	}

	// Connects to Java environment
	JavaNode.connectClientSocket();
	
	// Starts events listening for communication
	JavaNode.setupListeners(ipcMain);
    WindowsNode.createMainWindow();
    
    addFatalErrorEvent();
    
    JavaNode.CLIENT.write("mega\r");
});

function addFatalErrorEvent() {
	    ipcMain.on("fatal-error", (data, message) => {
		switch(message) {
			case("exit"):
				let options = {
					title: "Exit",
					message: "Are you sure you want to close Maets ?\nAny current download will be corrupted :)",
					type: "warning",
					buttons: ["Yes", "No"],
				};
				var choosenButton = dialog.showMessageBoxSync(WindowsNode.MAIN_WINDOW, options);
				
				if(choosenButton == 0){
					app.quit();
				}
				
				break;
			case("megacmd-not-installed"):
				UtilNode.fatalErrorPopup("MegaCMD not Installed", "Please install MegaCMD in it's default path.");
				break;
			case("unexpected"):
				UtilNode.fatalErrorPopup("MegaCMD error", "An unexpected error ocurred.")
				break;
		}
	})
}

function isJava() {
	let isJava = false;
	for(let i = 0; i < process.argv.length; i++) {
		console.log("Argument[" + i + "] === " + process.argv[i]);
		if(process.argv[i] === "java") {
			isJava = true;
		}
	}
	
	return isJava;
}