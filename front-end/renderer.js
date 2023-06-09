const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');

let loginResponseCode;

ipcRenderer.on('java-backend-response', (event, response) => {
    console.log('Response:', response);
    
    if (response && response.startsWith('login ')) {
      const responseCode = response.split(' ')[1];
      loginResponseCode = responseCode.replace('\r\n', '').replace(' ','');
  
      const numericCode = responseCode;
      LoginResponseHandler.handle({ code: numericCode });
      console.log('response: ', loginResponseCode);
      if (loginResponseCode === '0') {
        console.log(`${emailInput.value} ${passwordInput.value}`);
        loadscreen.style.display = 'none';
        anotherScreen.style.display = 'block';
      } else {
        loginform.reportValidity();
        loadscreen.style.display = 'none';
        logincard.style.display = 'block';
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
  anotherScreen.style.display = 'none';
  logincard.style.display = 'block';
})

loginButton.addEventListener('click', (event) => {
    event.preventDefault();
    
    if (loginform.checkValidity() && isValidEmail(emailInput.value)) {
      logincard.style.display = 'none';
      loadscreen.style.display = 'block';

      ipcRenderer.send('send-to-backend', `login ${emailInput.value} ${passwordInput.value}`);
      } else {
        loginform.reportValidity();
      }
    });