const { ipcRenderer } = require('electron');

ipcRenderer.on('java-backend-response', (event, response) => {
    const messageElement = document.getElementById('response');
    messageElement.textContent = response;
});

/* ipcRenderer.on('log-message', (event, message) => {
    const logElement = document.getElementById('log');
    logElement.textContent = message;
})

const myButton = document.getElementById('button');

myButton.addEventListener('click', () => {
    ipcRenderer.send('send-to-backend', 'buttonPress');
})*/

const logincard = document.getElementById('logincard');
const anotherScreen = document.getElementById('anotherScreen');
const loginButton = document.getElementById('loginButton');
const loginForm = document.querySelector('form');

loginButton.addEventListener('click', () => {
    logincard.remove;
    anotherScreen.style.display = 'block';
    anotherScreen.style.visibility = 'visible';
  });
