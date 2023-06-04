const { app , BrowserWindow, ipcMain } = require('electron');
const { ClientRequest } = require('http');
const path = require('path');

function createClientSocket(mainWindow) {
    // Connect to java backend
    const net = require('net');
    const client = new net.Socket();

    client.connect(1234, 'localhost', () => {
        log('Connected to java backend !')

        const request = 'Hello from Electron!\r';
        client.write(request);
    })

    client.on('data', (data) => {
        const response = data.toString();

        mainWindow.webContents.send('java-backend-response', response);
    })

    client.on('close', () => {
        log('Disconnected from Java backend');
    })

    client.on('error', (error) => {
        log('Error in electron: ' + error);
    })

    function log(string) {
        mainWindow.webContents.send('log-message', string);
    }

    ipcMain.on('send-to-backend', (event, message) => {
        client.write(message + '\r');
    })

    return client;
}

function createWindow() {
    const mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            //preload: path.join('C:\ElectronTest', 'renderer.js'),
        },
    });

    mainWindow.loadFile('main.html');

    // WebContents class send handle 'did-finish-load' when html is fully loaded 
    mainWindow.webContents.on('did-finish-load', () => {
       var client = createClientSocket(mainWindow);
       //client.write('html finished load\r');
    })
}

app.whenReady().then(createWindow);