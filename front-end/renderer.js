const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');
  
ipcRenderer.on('java-backend-response', (event, response) => {
    switch(response[0]) {
        case 'login' :
            switch(response[1]){
                case "0" :
                    document.getElementById("loadingScreen").style.display = "none";
                    document.getElementById("anotherScreen").style.display = "block";
                    document.getElementById("logincard").remove();
                    break;
                default :
                    document.getElementById("error").textContent = LoginResponseHandler.responseMessages[response[1]].message;
                    document.querySelector('form').reportValidity();
                    document.getElementById("loadingScreen").style.display = "none";
                    document.getElementById("logincard").style.display = "block";
                    break;
            }
            break;

        case 'isLoggedIn' :
            if(response[1] === 'true') ipcRenderer.send("send-to-backend", "firstName");
            else invokeLoginCard();
            break;
            
        case "mega" :
            ipcRenderer.send("send-to-backend", "isLoggedIn");
            break;
                
        case "firstName" : 
            showAnotherScreen(response[1]);
            break;

        case "lastName" : 

            break;
    }
});

function invokeLoginCard() {
    const logincard = document.createElement("div");
        logincard.id = "logincard";
    const title = document.createElement("h1");
        title.id = "title";
        title.textContent = "Welcome!";
    const subtitle = document.createElement("h2");
        subtitle.textContent = "Login or signup below.";
        subtitle.id = "subtitle";
    const form = document.createElement("form");
        const usernameLabel = document.createElement("label");
            usernameLabel.htmlFor = "username";
            usernameLabel.className = "attfield";
            usernameLabel.textContent = "EMAIL:";
        const inputEmail = document.createElement("input");
            inputEmail.type = "text";
            inputEmail.id = "email";
            inputEmail.className = "attinput";
            inputEmail.required = true;
        const passwordLabel = document.createElement("label");
            passwordLabel.htmlFor = "password";
            passwordLabel.className = "attfield";
            passwordLabel.textContent = "PASSWORD:";
        const inputPassword = document.createElement("input");
            inputPassword.type = "password";
            inputPassword.id = "password";
            inputPassword.className = "attinput";
            inputPassword.required = true;
        const breakLine = document.createElement("br");
        const loginButton = document.createElement("button");
            loginButton.id = "loginButton";
            loginButton.type = "button";
            loginButton.className = "attscreen";
            loginButton.textContent = "LOGIN";
    const divError = document.createElement("div");
        divError.id = "error";

    loginButton.addEventListener('click', (event) => {
        event.preventDefault();
        
        if (form.checkValidity() && isValidEmail(inputEmail.value)) {
          logincard.style.display = 'none';
          document.getElementById('loadingScreen').style.display = "block";
    
          ipcRenderer.send('send-to-backend', `login ${inputEmail.value} ${inputPassword.value}`);
        } else {
            form.reportValidity();
        }
    });

    document.getElementById("loadingScreen").hidden = true;
    form.append(usernameLabel, inputEmail, passwordLabel, inputPassword, breakLine, loginButton);
    logincard.append(title, subtitle, form, divError);
    document.body.appendChild(logincard);
}

function showAnotherScreen(username) {
    document.getElementById("sideHeader").childNodes[1].textContent = username;
    const logoutButton = document.getElementById("logoutButton");
    const anotherScreen = document.getElementById("anotherScreen");
    logoutButton.addEventListener('click', (event) => {
        console.log("LOGOUT CLICK");
        ipcRenderer.send('send-to-backend', 'logout');
        invokeLoginCard();
        anotherScreen.style.display = "none";
    });

    anotherScreen.hidden = false;
    anotherScreen.style.display = "block";
    document.getElementById("loadingScreen").style.display = "none";
}

function showHomepage() {
    document.getElementById('shop').style.display = "none";
    document.getElementById('library').style.display = "none";
    document.getElementById('homepage').style.display = "block";
}

function showLibrary() {
    document.getElementById('shop').style.display = "none";
    document.getElementById('homepage').style.display = "none";
    document.getElementById('library').style.display = "block";
}

function showShop() {
    document.getElementById('library').style.display = "none";
    document.getElementById('homepage').style.display = "none";
    document.getElementById('shop').style.display = "block";
}