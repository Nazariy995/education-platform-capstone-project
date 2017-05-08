function Service($http, appSettings, SessionService) {
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
    this.init();
}

Service.prototype.init = function () {
    var self = this;
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

Service.prototype.getAdminstructors = function () {
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/admin/user/list';

    return self._$http
        .get(url, self.config)
        .then(function (res) {
            //console.log("Get Adminstructors");
            return res.data;
        });
}

Service.prototype.addUser = function (payload){
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/admin/user';

    return self._$http
        .post(url, payload, self.config)
        .then(function (res) {
            return res.data;
        });
}

Service.prototype.editUser = function (email, payload) {
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/admin/user/' + email;
    self.getConfig();

    return self._$http
        .put(url, payload, self.config)
        .then(function (res) {
            Console.log("Getting user");
            return res.data;
        });
}

module.exports = angular.module('app.models.admin', [
    'app.settings'
]).service('AdminService', Service);
