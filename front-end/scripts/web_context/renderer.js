const { ipcRenderer, shell } = require("electron");
const { RendererListeners } = require("./scripts/web_context/RendererListeners");
const { LoginScreen } = require("./scripts/web_context/LoginScreen");

RendererListeners.addNodeListeners();


function switchLoadingScreen() {
	
	const loadingScreen = document.getElementById("loadingScreen");

	// Block = Is being Shown | None = Is Hided
	const displayValue = window.getComputedStyle(loadingScreen).getPropertyValue("display");
	if(displayValue === "block") {
		loadingScreen.style.setProperty("display", "none");
	} else {
		loadingScreen.style.setProperty("display", "block");
	}
}