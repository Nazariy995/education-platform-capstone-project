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

Service.prototype.getAdminstructors = function () {
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/admin/user/list';

    return this._$http
        .get(url, self.config)
        .then(function (res) {
            console.log("Get Adminstructors");
            return res.data;
        });
}

Service.prototype.getUser = function (payload) {
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/admin/user';

    return self._$http
        .post(url, payload, self.config)
        .then(function (res) {
            return res.data;
        });
}

module.exports = angular.module('app.models.admin', [
    'app.settings'
]).service('AdminService', Service);