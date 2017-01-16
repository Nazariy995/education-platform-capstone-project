angular
    .module('hello', [ 'ngRoute' ])
    .constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed'
})
