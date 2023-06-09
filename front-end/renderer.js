const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');

let loginResponseCode;

ipcRenderer.on('java-backend-response', (event, response) => {
    console.log('Response:', response);
    
    if (response && response.startsWith('login ')) {
        const responseCode = response.split(' ')[1];
        loginResponseCode = responseCode;

        const numericCode = responseCode;
        LoginResponseHandler.handle({ code: numericCode });
    }
    console.log('logincode:', loginResponseCode);
});

const loginform = document.querySelector('form');
const logincard = document.getElementById('logincard');
const anotherScreen = document.getElementById('anotherScreen');
const loginButton = document.getElementById('loginButton');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

loginButton.addEventListener('click', (event) => {
    event.preventDefault();
    
    if (loginform.checkValidity() && isValidEmail(emailInput.value)) {
        console.log(`${emailInput.value} ${passwordInput.value}`)
        logincard.remove();
        anotherScreen.style.display = 'block';
        ipcRenderer.send('send-to-backend', `login ${emailInput.value} ${passwordInput.value}`);
      } else {
        loginform.reportValidity();
      }
    });