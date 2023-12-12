class LoginScreen {
	
	// 1 = Mega | 2 = Drive
	static cloud_service = 1;
	
	static invoke() {
		const loginScreen = document.createElement("div");
		loginScreen.id = "loginScreen";
		
		const leftSideBar = document.createElement("div");
		leftSideBar.className = "side-bar";
		
			const leftStripe1 = document.createElement("div");
			leftStripe1.className = "stripe";
			
			const leftStripe2 = document.createElement("div");
			leftStripe2.className = "stripe";
			
			const leftStripe3 = document.createElement("div");
			leftStripe3.className = "stripe";
			
			const leftStripe4 = document.createElement("div");
			leftStripe4.className = "stripe";
			
		const rightSideBar = document.createElement("div");
		rightSideBar.className = "side-bar"
		rightSideBar.id = "side-bar-r";
		
			const rightStripe1 = document.createElement("div");
			rightStripe1.className = "stripe";
			
			const rightStripe2 = document.createElement("div");
			rightStripe2.className = "stripe";
			
			const rightStripe3 = document.createElement("div");
			rightStripe3.className = "stripe";
			
			const rightStripe4 = document.createElement("div");
			rightStripe4.className = "stripe";
			
		const leftSupportSideBar = document.createElement("div");
		leftSupportSideBar.className = "support-side-bar";
		
		const rightSupportSideBar = document.createElement("div");
		rightSupportSideBar.className = "support-side-bar";
		rightSupportSideBar.id = "support-side-bar-r";
			
		const loginCardTitle = document.createElement("h1");
		loginCardTitle.className = "login-card-titles";
		loginCardTitle.id = "login-card-title";
		loginCardTitle.textContent = "Ready to Play?";
		
		const loginCardSubtitle = document.createElement("h2");
		loginCardSubtitle.className = "login-card-titles";
		loginCardSubtitle.id = "login-card-subtitle";
		loginCardSubtitle.textContent = "Log in below";
		
		const loginForm = document.createElement("div");
		loginForm.id = "login-form";
		
			const emailTitle = document.createElement("h1");
			emailTitle.textContent = "Email:";
			
			const emailInput = document.createElement("input");
			emailInput.type = "text";
			emailInput.placeholder = "Email";
			emailInput.id = "login-form-email";
			
			const passwordTitle = document.createElement("h1");
			passwordTitle.textContent = "Password:";
			
			const passwordInput = document.createElement("input");
			passwordInput.type = "text";
			passwordInput.placeholder = "Password";
			passwordInput.id = "login-form-password";
			
			const loginButton = document.createElement("button");
			loginButton.id = "login-button";
			loginButton.className = "login-form-button";
			loginButton.textContent = " Login ";
			
			const loginFormDetails = document.createElement("div");
			loginFormDetails.id = "login-form-details";
				const leftDetailBar = document.createElement("h2");
				
				const createAccountSubtitle = document.createElement("div");
				createAccountSubtitle.textContent = " Or Create Account ";
				
				const rightDetailBar = document.createElement("h2");
				rightDetailBar.id = "login-form-detail-r";
		
			const signupButton = document.createElement("button");
			signupButton.id = "signup-button";
			signupButton.className = "login-form-button";
			signupButton.textContent = " Sign Up ";
			
			const cloudServices = document.createElement("div");
			cloudServices.id = "cloud-service";
				const megaButton = document.createElement("button");
				megaButton.id = "mega";
					const megaImage = document.createElement("img");
					megaImage.src = "./image_contents/mega.png";
					megaImage.id = "mega-image";
					megaImage.className = "cloud-service-image";
					
					//REMOVE THIS FILTER WHEN DRIVE BE IMPLEMENTED
					megaImage.style.filter = "grayscale(0)";
					
				const driveButton = document.createElement("button");
				driveButton.id = "drive";
				driveButton.className = "cloud-service-button";
					const driveImage = document.createElement("img");
					driveImage.src = "./image_contents/drive.png";
					driveImage.id = "drive-image";
					driveImage.className = "cloud-service-image";
					
					//REMOVE THIS FILTER WHEN DRIVE BE IMPLEMENTED
					driveImage.style.filter = "grayscale(1)";
		
		megaButton.addEventListener("click", function() {LoginScreen.cloudServiceButton(megaButton)});
		driveButton.addEventListener("click", function() {alert("Not Supported")});
		//driveButton.addEventListener("click", function() {LoginScreen.cloudServiceButton(driveButton)});
		
		loginButton.addEventListener("click", LoginScreen.loginButton);		
		signupButton.addEventListener("click", LoginScreen.signupButton);
		
		megaButton.appendChild(megaImage);
		driveButton.appendChild(driveImage);
		
		cloudServices.append(megaButton, driveButton);
		
		loginFormDetails.append(leftDetailBar, createAccountSubtitle, rightDetailBar);
		
		loginForm.append(emailTitle, emailInput, passwordTitle, passwordInput, loginButton, loginFormDetails, signupButton, cloudServices);
		
		leftSideBar.append(leftStripe1, leftStripe2, leftStripe3, leftStripe4);
		rightSideBar.append(rightStripe1, rightStripe2, rightStripe3, rightStripe4);
		
		loginScreen.append(leftSideBar, rightSideBar, leftSupportSideBar, rightSupportSideBar, loginCardTitle, loginCardSubtitle, loginForm);
		
		document.body.appendChild(loginScreen);
	}
	
	static loginButton() {
		
	}
	
	static signupButton() {
		switch(LoginScreen.cloud_service) {
			case 1: {
				shell.openExternal("https://mega.nz/register");
				break;
			}
			
			case 2: {
				shell.openExternal("https://www.google.com/intl/en-us/drive/");
				break;
			}
		}
	}
	
	static cloudServiceButton(button) {
		if(button.id === "mega") {
			LoginScreen.cloud_service = 1;
			document.getElementById("drive").firstChild.style.setProperty("filter", "grayscale(1)");
		} else {
			LoginScreen.cloud_service = 2;
			document.getElementById("mega").firstChild.style.setProperty("filter", "grayscale(1)");
		}
		
		button.firstChild.style.setProperty("filter", "grayscale(0)");
	}
}

module.exports = { LoginScreen };