/*
Description: Get, set user data
*/

function Service($http, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
};

Service.prototype.getConfig = function(){
    var self = this;
    var config = {
        headers : {
            'Accept' : 'application/json'
        }
    };
    return config;
};

Service.prototype.newPassword = function(payload){
    var self =this;
    var config = self.getConfig();

    var url = self._appSettings.API.basePath
    + "/rest/self/password/change";

    return this._$http
          .post(url, payload, config)
          .then(function (res) {
            return res.data;
          });
};

Service.prototype.resetPassword = function(payload){
    var self =this;
    var config = self.getConfig();

    var url = self._appSettings.API.basePath
    + "/rest/self/password/reset";

    return this._$http
          .post(url, payload, config)
          .then(function (res) {
            return res.data;
          });
};

//Retrieve the correct API URL based on the user role
Service.prototype.getUrl = function(url){
    var self = this;
    var user = self._SessionService.getUser();
    if(user.roles.indexOf(self.userRoles.user) != -1){
        return url[self.userRoles.user];
    } else if (user.roles.indexOf(self.userRoles.instructor) != -1){
        return url[self.userRoles.instructor];
    }
};



module.exports = angular.module('app.models.user', [
    'app.settings'
]).service('UserService', Service);




