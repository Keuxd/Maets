const { ipcRenderer } = require('electron');

// Autenticação/validação de resposta do backend no login
class LoginResponseHandler {
  static responseMessages = {
    0: { message: 'Login Success', canProceed: true },
    9: { message: 'Usuário ou senha incorretos', canProceed: false },
    54: { message: 'Already logged in. Please log out first.', canProceed: false },
  };

  static handle(response) {
    const messageElement = document.getElementById('response');
    const errorElement = document.getElementById('error');
  
    const { message, canProceed } = LoginResponseHandler.responseMessages[response.code] || { message: 'Unknown error occurred', canProceed: false };
  
    messageElement.textContent = message;
    errorElement.textContent = canProceed ? '' : message;
  
    if (canProceed) {
      const logincard = document.getElementById('logincard');
      const anotherScreen = document.getElementById('anotherScreen');
      logincard.remove();
      anotherScreen.style.display = 'block';
    }
  }
}

// Verificar se o email foi escrito de maneira adequada
function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// Permitir que as funções sejam exportadas para outros arquivos
module.exports = { LoginResponseHandler, isValidEmail };