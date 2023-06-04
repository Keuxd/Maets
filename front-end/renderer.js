//window.nodeRequire = require;
const { ipcRenderer } = require('electron');

ipcRenderer.on('java-backend-response', (event, response) => {
    alert(response);
    const messageElement = document.getElementById('response');
    messageElement.textContent = response;
});

ipcRenderer.on('log-message', (event, message) => {
    const logElement = document.getElementById('log');
    logElement.textContent = message;
})

const myButton = document.getElementById('button');

myButton.addEventListener('click', () => {
    ipcRenderer.send('send-to-backend', 'buttonPress');
})