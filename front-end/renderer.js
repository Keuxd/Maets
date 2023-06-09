const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');

let loginResponseCode;
var test = false;

ipcRenderer.on('java-backend-response', (event, response) => {
    console.log('Response:', response);
    
    if (response && response.startsWith('login ')) {
      const responseCode = response.split(' ')[1];
      loginResponseCode = responseCode;
  
      const numericCode = responseCode;
      LoginResponseHandler.handle({ code: numericCode });
  
      if (loginResponseCode === '0') {
        console.log(`${emailInput.value} ${passwordInput.value}`)
        loadscreen.style.visibility = 'hidden';
        logincard.style.visibility = 'hidden';
        anotherScreen.style.visibility = 'visible';
      } else {
        loginform.reportValidity();
        loadscreen.style.visibility = 'hidden';
        logincard.style.visibility = 'visible';
      }
    }
    console.log('logincode:', loginResponseCode);
});

const loginform = document.querySelector('form');
const logincard = document.getElementById('logincard');
const loadscreen = document.getElementById('loadingScreen');
const anotherScreen = document.getElementById('anotherScreen');
const loginButton = document.getElementById('loginButton');
const logoutButton = document.getElementById('logoutButton');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

logoutButton.addEventListener('click', (event) => {
  ipcRenderer.send('send-to-backend', "logout");
})

loginButton.addEventListener('click', (event) => {
    event.preventDefault();
    
    if (loginform.checkValidity() && isValidEmail(emailInput.value)) {
      logincard.style.visibility = 'hidden';
      loadscreen.style.visibility = 'visible;'

        ipcRenderer.send('send-to-backend', `login ${emailInput.value} ${passwordInput.value}`);
      } else {
        loginform.reportValidity();
      }
    });