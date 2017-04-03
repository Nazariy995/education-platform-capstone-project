function Controller($state, $stateParams, AdminService) {
    "ngInject";
    this._$state = $state;
    this.pageName = "Admin";
    this._AdminService = AdminService;
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function () {
    var self = this;
}

module.exports = angular.module('app.views.admin.home.controller', [])
    .controller('AdminCtrl', Controller);