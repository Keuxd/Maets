/* Codes reference
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
        
    + Logout:
        - 0 : Logout Success
    
    + Quit:
        - 0 : Quit Success
        
*/// PS: All responses use this model: 'command response_code'
//// Example: 'login 54'

/* Communication 
    - Login : "login email password"

*/// PS: All arguments should split using spaces