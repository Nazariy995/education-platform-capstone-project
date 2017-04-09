function Controller($state, $stateParams, AdminService) {
    "ngInject";
    this._$state = $state;
    this.pageName = "Admin_Add_Edit";
    this._AdminService = AdminService;
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function () {
    var self = this;
    self.getConfig();
}

module.exports = angular.module('app.views.admin.adminAddEdit.controller', [])
    .controller('AdminAddEditCtrl', Controller);