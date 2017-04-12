function Controller($state, $stateParams, AdminService) {
    "ngInject";
    this._$state = $state;
    this.pageName = "Admin";
    this._AdminService = AdminService;
    this.created_updated = $stateParams.created_updated;
    this.users = [];
<<<<<<< HEAD
    this.email = $stateParams.email;
    this.appRole = $stateParams.appRole;
    this.firstName = $stateParams.firstName;
    this.lastName = $stateParams.lastName;
=======
>>>>>>> parent of e21494a... Redirect after adding user
    this.init();
};

Controller.prototype.init = function () {
    var self = this;
    console.log("Attempting to grab instructors and admins..")
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

<<<<<<< HEAD
Controller.prototype.viewUserDetails = function (userData) {
    var self = this;
    var params = {
        firstName: userData.firstName,
        lastName: userData.lastName,
        email: userData.email,
        appRoles: userData.appRoles
    };
    console.log(userData);
    self._$state.go('app.admin.add_edit', params, { reload: true });
}

Controller.prototype.editMember = function (email, user) {
    var self = this;
    self._AdminService.editMember(email)
        .then(function (user) {
            self.user = user;
        }, function (err) {
            self.error = err;
        });
}

=======
>>>>>>> parent of e21494a... Redirect after adding user
module.exports = angular.module('app.views.admin.home.controller', [])
    .controller('AdminCtrl', Controller);