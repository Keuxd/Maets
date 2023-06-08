const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');

ipcRenderer.on('java-backend-response', (event, response) => {
    console.log('Response:', response);
    LoginResponseHandler.handle(response);
});

const loginform = document.querySelector('form');
const logincard = document.getElementById('logincard');
const anotherScreen = document.getElementById('anotherScreen');
const loginButton = document.getElementById('loginButton');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

loginButton.addEventListener('click', () => {
    event.preventDefault();
    
    if (loginform.checkValidity() && isValidEmail(emailInput.value)) {
        logincard.remove();
        anotherScreen.style.display = 'block';
    
        // Enviar solicitação de login para o backend
        ipcRenderer.send('send-to-backend', `login ${emailInput} ${passwordInput}`);
      } else {
        loginform.reportValidity();
      }
    });