function Controller($state, $stateParams, AdminService) {
    "ngInject";
    this._$state = $state;
    this.pageName = "Admins / Instructors";
    this._AdminService = AdminService;
    this.created_updated = $stateParams.created_updated;
    this.users = [];
    this.user;
    this.init();
};

Controller.prototype.init = function () {
    var self = this;
    self.getMembers();
}

Controller.prototype.getMembers = function () {
    var self = this;
    self._AdminService.getAdminstructors()
        .then(function (payload) {
            self.users = payload;
        }, function (err) {
            self.error = err;
        });
};

Controller.prototype.editMember = function (email, user) {
    var self = this;
    self._AdminService.editMember(email)
        .then(function (user) {
            self.user = user;
        }, function (err) {
            self.error = err;
        });
}

module.exports = angular.module('app.views.admin.home.controller', [])
    .controller('AdminCtrl', Controller);