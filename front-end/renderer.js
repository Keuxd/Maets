const { ipcRenderer } = require('electron');
const { LoginResponseHandler, isValidEmail } = require('./auth');

ipcRenderer.on("java-backend-json", (event, response) => {
    switch (response.type) {
		case "Error" :
			ipcRenderer.send("fatal-error", "unexpected");
			break;
			
        case "Shop":
			const container = document.getElementById("container");
		    const productList = document.createElement("ul");
		    productList.className = "product-list";
		    
		    for(i = 0; i < response.games.length; i++) {
				const game = response.games[i];
				
				const element = document.createElement("li");
				const a = document.createElement("a");
				a.onclick = function() {
					clearContainer();
					invokeGame(game);
				};
				
				const image = document.createElement("img");
				image.src = "./image_contents/" + game.id + "_banner.jpg";
				image.alt = game.name + " Banner";
				
				const span = document.createElement("span");
				span.className = "product-name";
				span.innerText = game.name;
				
				a.append(image, span);
				element.append(a);
				productList.append(element);
			}
		    container.append(productList);
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
            
        case "isInLibrary":
			const container = document.getElementById("container");
			const button = document.createElement("button");
			button.innerText = "Add to Library";
			button.id = "addToLibraryButton";
			button.onclick = function() {
				ipcRenderer.send("send-to-backend","addToLibrary " + response[1]);
				button.remove();
			};
			
			if(response[2] === "false")
				container.append(button);
				
			break;

        case "mega":
			switch(response[1]) {
				case "-1":
					ipcRenderer.send("fatal-error", "unexpected");
					break;
				case "2":
					ipcRenderer.send("fatal-error", "megacmd-not-installed");
					break;
				default:
            		ipcRenderer.send("send-to-backend", "isLoggedIn");
					break;
			}
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
    libraryButton.onclick = invokeOptions;
    const userNameSideHeader = document.createElement("div");
    userNameSideHeader.id = "sideHeader";
    const name = document.createElement("p");
    name.textContent = username;
    userNameSideHeader.appendChild(name);

    const logoutButton = document.createElement("button");
    logoutButton.id = "logoutButton";
    logoutButton.onclick = logout;
    logoutButton.textContent = "Logout";
    
    const exitButton = document.createElement("button");
    exitButton.id = "exitButton";
    exitButton.onclick = exit;
	exitButton.textContent = "Exit";
	
    const container = document.createElement("div");
    container.id = "container";
    
    const backgroundImageContainer = document.createElement("div");
    backgroundImageContainer.id = "backgroundImageContainer";

    document.getElementById("loadingScreen").style.display = "none";
    container.append(backgroundImageContainer);
    mainScreen.append(userProfilePicture, homeButton, shopButton, libraryButton, userNameSideHeader, logoutButton, exitButton, container);
    document.body.appendChild(mainScreen);
}

function invokeHomePage() {
    clearContainer();

    const container = document.getElementById("container");
    const container1 = document.createElement("div");
    container1.id = "about1";
    const homepage1 = document.createElement("div");
    homepage1.id = "homepage";
    homepage1.textContent = "About Us";
    const abtus = document.createElement("p");
    abtus.id = "abtus";
    abtus.innerHTML = "Welcome to <span style = \"color: #e0d921\">Maets</span> ! That's our way to <br> give you easy acess to games in <br> a free way!";
    const container2 = document.createElement("div");
    container2.id = "about2";
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

    container.append(container1, container2);
}

function invokeShop() {
    clearContainer();
    
    ipcRenderer.send("send-to-backend", "shop");
}

function invokeOptions() {
    clearContainer();

    const container = document.getElementById("container");
    const sample = document.createElement("div");
    sample.id = "options";
    container.append(sample);
}

function invokeGame(game) {
//	document.getElementById("backgroundImageContainer").style.backgroundImage = "url('./image_contents/" + game.id + "_background.jpg')";
	ipcRenderer.send("send-to-backend", "isInLibrary " + game.id);
	
	const container = document.getElementById("container");
	
	const gameBanner = document.createElement("img");
	gameBanner.src = "./image_contents/" + game.id + "_background.jpg";
	gameBanner.id = "gameBackground";
	
	const description = document.createElement("div");
	description.id = "gameDescription"
	description.innerText = game.description;
	
	const gameButtonsDiv = document.createElement("div");
	gameButtonsDiv.id = "gameButtons";

	const gameTitle = document.createElement("a");
	gameTitle.innerText = game.name;
	gameTitle.id = "";
	
	const addToLibraryButton = document.createElement("button");
	addToLibraryButton.innerText = "Add To Library"; 
	
	gameButtonsDiv.append(gameTitle, addToLibraryButton);
	container.append(gameBanner, description, gameButtonsDiv);
}

function logout() {
    ipcRenderer.send("send-to-backend", "logout");
    document.getElementById("mainScreen").remove();
    document.getElementById("loadingScreen").style.display = "block";
}

function exit() {
	ipcRenderer.send("fatal-error", "exit");
}

function clearContainer() {
    const container = document.getElementById("container");
    
	while(container.firstChild) {
		container.removeChild(container.firstChild);
	}
	
	const backgroundImageContainer = document.createElement("div");
	backgroundImageContainer.id = "backgroundImageContainer";
	container.append(backgroundImageContainer);
}
