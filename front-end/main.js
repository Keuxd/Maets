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
        width: 1280,
        height: 720,
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

	console.log(process.argv[2]);

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
			case("megacmd-not-installed"):
				BrowserWindow.getFocusedWindow().hide();
				dialog.showErrorBox("MegaCMD not Installed", "Please install MegaCMD in it's default path.");
				break;
			case("unexpected"):
				BrowserWindow.getFocusedWindow().hide();
				dialog.showErrorBox("MegaCMD error", "An unexpected error ocurred.");
				break;
		}
		
		app.quit();
	})
    
    client.write("mega\r");
});

function isJson(string) {
	try {
		return [true, JSON.parse(string)];
	} catch(exception) {
		return [false, null];
	}
}
