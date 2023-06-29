const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');

ipcRenderer.on("java-backend-json", (event, response) => {
    switch (response.type) {
		case "Error" :
			// Mostra uma mensagem de erro e fecha o programa
			break;
			
        case "Shop":
            const containerShop = document.createElement("div");
            containerShop.id = "containerShop";
            const containers = document.getElementById("containers");

            response.games.forEach(game => {				
                const divShop = document.createElement("div");
                divShop.className = "shopDivs"

                const gameTitle = document.createElement("h2");
                gameTitle.className = "shopTitles";
                gameTitle.innerText = game.name;

                const gameDesc = document.createElement("p");
                gameDesc.className = "shopDescs";
                gameDesc.innerText = game.description;

                divShop.appendChild(gameTitle);
                divShop.appendChild(gameDesc);

                containerShop.appendChild(divShop);
            });
            containers.append(containerShop);
            console.log("Shop Json Received ?");
            break;
            
        case "Library":
            console.log("Lib Json Received ?");
            break;
    }
});

ipcRenderer.on('java-backend-response', (event, response) => {
    switch (response[0]) {
        case 'login':
            switch (response[1]) {
                case "0":
                    document.getElementById("logincard").remove();
                    ipcRenderer.send("send-to-backend", "firstName");
                    break;
                default:
                    document.getElementById("error").textContent = LoginResponseHandler.responseMessages[response[1]].message;
                    document.querySelector('form').reportValidity();
                    document.getElementById("loadingScreen").style.display = "none";
                    document.getElementById("logincard").style.display = "block";
                    break;
            }
            break;

        case 'isLoggedIn':
            if (response[1] === 'true') ipcRenderer.send("send-to-backend", "firstName");
            else invokeLoginCard();
            break;

        case "mega":
            ipcRenderer.send("send-to-backend", "isLoggedIn");
            break;

        case "firstName":
            invokeMainScreen(response[1]);
            invokeHomePage();
            break;

        case "logout":
            document.getElementById("loadingScreen").style.display = "none";
            invokeLoginCard();
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

    inputPassword.addEventListener('input', toggleLoginButtonState);
    inputEmail.addEventListener('input', toggleLoginButtonState);

    function toggleLoginButtonState() {
        if (inputEmail.value !== '' && inputPassword.value !== '') {
            loginButton.style.backgroundColor = '#e0d921';
            loginButton.style.borderColor = '#e0d921';
            loginButton.style.color = '#5a5712';
        } else {
            loginButton.style.backgroundColor = '#5e5e5e';
            loginButton.style.borderColor = '#5e5e5e';
            loginButton.style.color = 'white';
        }
    }
}

function invokeMainScreen(username) {
    const mainScreen = document.createElement("div");
    mainScreen.id = "mainScreen";
    const userProfilePicture = document.createElement("img");
    userProfilePicture.id = "icon";
    userProfilePicture.src = "./image_contents/Nobara.jpg";
    const homeButton = document.createElement("img");
    homeButton.id = "button1";
    homeButton.src = "./image_contents/home.png";
    homeButton.onclick = invokeHomePage;
    const shopButton = document.createElement("img");
    shopButton.id = "button2";
    shopButton.src = "./image_contents/shop.png";
    shopButton.onclick = invokeShop;
    const libraryButton = document.createElement("img");
    libraryButton.id = "button3";
    libraryButton.src = "./image_contents/library.png";
    libraryButton.onclick = invokeLibrary;
    const userNameSideHeader = document.createElement("div");
    userNameSideHeader.id = "sideHeader";
    const name = document.createElement("p");
    name.textContent = username;
    userNameSideHeader.appendChild(name);

    const logoutButton = document.createElement("button");
    logoutButton.id = "logoutButton";
    logoutButton.onclick = logout;
    logoutButton.textContent = "Logout";

    const containers = document.createElement("div");
    containers.id = "containers";

    document.getElementById("loadingScreen").style.display = "none";
    mainScreen.append(userProfilePicture, homeButton, shopButton, libraryButton, userNameSideHeader, logoutButton, containers);
    document.body.appendChild(mainScreen);
}

function invokeHomePage() {
    clearContainers();

    const containers = document.getElementById("containers");
    const container1 = document.createElement("div");
    container1.id = "container";
    const homepage1 = document.createElement("div");
    homepage1.id = "homepage";
    homepage1.textContent = "About Us";
    const abtus = document.createElement("p");
    abtus.id = "abtus";
    abtus.innerHTML = "Welcome to <span style = \"color: #e0d921\">Maets</span> ! That's our way to <br> give you easy acess to games in <br> a free way!";
    const container2 = document.createElement("div");
    container2.id = "container2";
    const homepage2 = document.createElement("div");
    homepage2.id = "homepage2";
    homepage2.textContent = "Patch Notes";
    const patchVer = document.createElement("p");
    patchVer.id = "patchVer";
    patchVer.textContent = "Update 01";
    const patchNote = document.createElement("p");
    patchNote.id = "patchNote";
    patchNote.innerHTML = "Added a Home Screen, with <br> some visual elements, with the <br> principal idea of this <br> software."

    container1.append(homepage1, abtus);
    container2.append(homepage2, patchVer, patchNote);

    containers.append(container1, container2);
}

function invokeShop() {
    clearContainers();
    ipcRenderer.send("send-to-backend", "shop");

    const containers = document.getElementById("containers");
    const sample = document.createElement("div");
    sample.id = "shop";
    containers.append(sample);
}

function invokeLibrary() {
    clearContainers();

    const containers = document.getElementById("containers");
    const sample = document.createElement("p");
    sample.id = "library";
    sample.innerHTML = "LIBRARY GONE BRRRRRRRRRRR";

    containers.append(sample);
}

function logout() {
    ipcRenderer.send("send-to-backend", "logout");
    document.getElementById("mainScreen").remove();
    document.getElementById("loadingScreen").style.display = "block";
}

function clearContainers() {
    const containers = document.getElementById("containers");
    while (containers.firstChild) {
        containers.removeChild(containers.firstChild);
    }
}