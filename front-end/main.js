const { app , BrowserWindow, ipcMain } = require('electron');

function createClientSocket(mainWindow) {
    // Connect to java backend
    const net = require('net');
    const client = new net.Socket();

    client.connect(1234, 'localhost', () => {
        console.log('Connected to java server');
    })

    client.on('data', (data) => {
        const response = data.toString().trim();
        console.log("+ Received: " + response);
        mainWindow.webContents.send('java-backend-response', response.split(' '));
    })

    client.on('close', () => {
        console.log('Java socket was closed');
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

function createWindow(isloggedIn) {
    const mainWindow = new BrowserWindow({
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

    return mainWindow;
}

app.whenReady().then(() => {
    const mainWindow = createWindow();
    const client = createClientSocket(mainWindow);
    client.write("mega\r");
});