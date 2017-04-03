function Service($http, appSettings, SessionService) {
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
    this.admin = [];
    this.instructor = [];
    this.init();
}

Service.prototype.init = function () {
    var self = this;
    console.log("Admin Controller init");
    self.getConfig();
};

Service.prototype.getConfig = function () {
    var self = this;
    self.config = {
        headers: {
            'Accept': 'application/json'
        }
    };
};

module.exports = angular.module('app.models.admin', [
    'app.settings'
]).service('AdminService', Service);