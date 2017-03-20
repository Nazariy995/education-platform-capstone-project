

function SessionService($window){
    "ngInject";

    this._$window = $window;
    try {
    	this._user = JSON.parse(localStorage.getItem('session.user'));
        this._accessToken = JSON.parse(localStorage.getItem('session.accessToken'));
        this._courseUserId = JSON.parse(localStorage.getItem('session.courseUserId'));
    } catch(e) {
    	this._user = null;
    	this._accessToken = null;
    	this._courseUserId = null;
    }
    
}

SessionService.prototype.getUser = function(){
    return this._user;
}

SessionService.prototype.setCourseUserId = function(courseUserId){
    console.log("Set new course user id");
    this._courseUserId = courseUserId;
    console.log(courseUserId);
    this._$window.localStorage.setItem('session.courseUserId', JSON.stringify(courseUserId));
}

SessionService.prototype.getCourseUserId = function(){
    var self = this;
    return self._courseUserId;
}

SessionService.prototype.setUser = function(user){
    this._user = user;
    this._$window.localStorage.setItem('session.user', JSON.stringify(user));
    return this;
}

SessionService.prototype.getAccessToken = function(){
    var self = this;
    return self._accessToken;
}

SessionService.prototype.setAccessToken = function(accessToken){
    var self = this;
    self._accessToken = accessToken;
    self._$window.localStorage.setItem('session.accessToken', JSON.stringify(accessToken));
    return self;
}

SessionService.prototype.destroy = function(){
    this.setUser(null);
    this.setAccessToken(null);
    this.setCourseUserId(null);
}

SessionService.prototype.create = function(user, accessToken){
    var tempUser = {
        "email" : user.email,
        "firstName" : user.firstName,
        "lastName" : user.lastName,
        "roles" : user.appRoles
    }
    this.setUser(tempUser);
    this.setAccessToken(accessToken);
    return tempUser;
}

module.exports = angular.module('app.components.services.session_service', [])
    .service('SessionService', SessionService);



