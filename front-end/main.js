const { app , BrowserWindow, ipcMain, dialog } = require('electron');

let mainWindow;

function createClientSocket() {
    // Connect to java backend
    const net = require('net');
    const client = new net.Socket();

    client.connect(1234, 'localhost', () => {
        console.log('Connected to java server');
    })

    client.on('close', () => {
        fatalErrorPopup("Server Error", "Server was closed, closing application..");
    })

    client.on('error', (error) => {
        console.log('Error in electron: ' + error);
    })

    ipcMain.on('send-to-backend', (event, message) => {
        console.log("- Sent: " + message);
        client.write(message + '\r');
    })

    return client;
}

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 1280, // 800 1280 1920
        height: 720, // 600  720 1080
        resizable: false,
        frame: false,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            //preload: path.join('C:\ElectronTest', 'renderer.js'),
        },
    });

    mainWindow.loadFile('main.html');

    // WebContents class send handle 'did-finish-load' when html is fully loaded 
    mainWindow.webContents.on('did-finish-load', () => {

    })
}

app.whenReady().then(() => {
	
	let isJava = false;
	for(let i = 0; i < process.argv.length; i++) {
		console.log("Argument[" + i + "] === " + process.argv[i]);
		if(process.argv[i] === "java") {
			isJava = true;
		}
	}
	
	if(!isJava) {
		fatalErrorPopup("Executable error", "Please open using Maets.bat");
		return;
	}

    const client = createClientSocket();
    createWindow();
    
    client.on('data', (data) => {
        const response = data.toString().trim();
        console.log("+ Received: " + response);
        
        const availableJson = isJson(response);
        if(availableJson[0]) {
			mainWindow.webContents.send("java-backend-json", availableJson[1]);
		} else {
        	mainWindow.webContents.send('java-backend-response', response.split(' '));
		}
    })
    
    ipcMain.on("fatal-error", (data, message) => {
		switch(message) {
			case("exit"):
				let options = {
					title: "Exit",
					message: "Are you sure you want to close Maets ?\nAny current download will be corrupted :)",
					type: "warning",
					buttons: ["Yes", "No"],
				};
				var choosenButton = dialog.showMessageBoxSync(mainWindow, options);
				
				if(choosenButton == 0){
					app.quit();
				}
				
				break;
			case("megacmd-not-installed"):
				fatalErrorPopup("MegaCMD not Installed", "Please install MegaCMD in it's default path.");
				break;
			case("unexpected"):
				fatalErrorPopup("MegaCMD error", "An unexpected error ocurred.")
				break;
		}
	})
    
    client.write("mega\r");
});

function fatalErrorPopup(title, content) {
	mainWindow.hide();
	dialog.showErrorBox(title, content);
	app.quit();
}

function isJson(string) {
	try {
		return [true, JSON.parse(string)];
	} catch(exception) {
		return [false, null];
	}
}
