function Controller($state, $stateParams, user, AdminService) {
    "ngInject";
    this._$state = $state;
    this.pageName = "Add/Edit Admin or Instructor";
    this._AdminService = AdminService;
    this.user = user;
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function () {
    var self = this;
}

Controller.prototype.addUser = function (valid, user) {
    var self = this;
    if (valid) {
        self.error = null;
        self._AdminService.addUser(user)
            .then(function (payload) {
                self._$state.go('app.admin', { created_updated: true });
        }, function(err) {
           self.error = "Error adding users";
        });
    }
};
module.exports = angular.module('app.views.admin.adminAddEdit.controller', [])
    .controller('AdminAddEditCtrl', Controller);
