class RendererListeners {
	
	static addNodeListeners() {
		ipcRenderer.on("node-message", (event, message) => {
			switch(message) {
				case "invokeLoginScreen": {
					LoginScreen.invoke(); 
					switchLoadingScreen();
					break;
				}
				
				case "invokeMainScreen": {
					break;
				}
				
			}
		});
	}
	
}

module.exports = { RendererListeners };