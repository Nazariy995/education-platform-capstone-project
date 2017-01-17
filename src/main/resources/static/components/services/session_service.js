

function SessionService($window){
    "ngInject";
    
    this._$window = $window;
    this._user = JSON.parse(localStorage.getItem('session.user'));
    this._accessToken = JSON.parse(localStorage.getItem('session.accessToken'));
}

SessionService.prototype.getUser = function(){
    return this._user;
}

SessionService.prototype.setUser = function(user){
    this._user = user;
    this._$window.localStorage.setItem('session.user', JSON.stringify(user));
    return this;
}

SessionService.prototype.getAccessToken = function(){
    return this._accessToken;
}

SessionService.prototype.setAccessToken = function(accessToken){
    this._accessToken = accessToken;
    this._$window.localStorage.setItem('serssion.accessToken', accessToken);
    return this;
}

SessionService.prototype.destroy = function(){
    this.setUser(null);
    this.setAccessToken(null);
}

SessionService.prototype.create = function(user, accessToken){
    this.setUser(user);
    this.setAccessToken = accessToken;
}

module.exports = angular.module('app.components.services.session_service', [])
    .service('SessionService', SessionService);



