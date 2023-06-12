class LoginResponseHandler {
  static responseMessages = {
    0: { message: 'Login Success'},
    9: { message: 'Usuário ou senha incorretos'},
    54: { message: 'Already logged in. Please log out first.'},
    13: { message: 'Login failed: unconfirmed account. Please confirm your account'}
  };
}

// Verificar se o email foi escrito de maneira adequada
function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// Permitir que as funções sejam exportadas para outros arquivos
module.exports = { LoginResponseHandler, isValidEmail };