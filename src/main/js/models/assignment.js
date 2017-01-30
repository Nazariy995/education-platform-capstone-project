/*
Description: Add, Get, Set, Delete Assignments
*/

function AssignmentService($http, appSettings){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;

}

AssignmentService.prototype.getAssignments = function(courseId){
    console.log(courseId);
    return this._$http
          .get(this._appSettings.API.basePath + '/rest/student/course/' + courseId + '/modules')
          .then(function (res) {
            return res.data;
          });
}

module.exports = angular.module('app.models.assignment', [
    'app.settings'
]).service('AssignmentService', AssignmentService);
