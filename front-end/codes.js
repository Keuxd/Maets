/* Codes reference
	+ Mega:
		-  2   : MegaCmd not installed
		-  0   : Server start success
		- (-1) : Unexpected Error
		- (-2) : Server start "success"
		
    + Login:
        - 0  : Login Success
        - 2  : Failed to Login: Invalid argument
        - 9  : Login Failed: Invalid email or password
        - 13 : Login Failed: Unconfirmed account. Please confirm your account
        - 54 : Already logged in. Please log out first.
    
    + isLoggedIn:
        - true  (Internal Code 0)
        - false (Internal Code 57)
    
    + firstName:
    + lastName:
        - 57 : Not logged in.
        - 0 : Name
        
    + addToLibrary: 
    	- 0 : Add Success
        - 2 : Game ID doesn't exist || Config file error
        - 9 : Invalid game id format(An integer is expected)
   
    + Logout:
        - 0 : Logout Success
    
    + Quit:
        - 0 : Quit Success
        
    # Game State:
    	- 0 : Not downloaded yet, can be downloaded
		- 1 : Downloaded, can be installed
		- 2 : Installed, ready to play
		- 9 : Error during download || Error during installation || Is Downloading or Installing
        
*/// PS: All responses use this model: 'command response_code'
//// Example: 'login 54'

/* Communication 
    - Login : "login email password"
    - Add game to library: "addToLibrary gameId"

*/// PS: All arguments should split using spaces