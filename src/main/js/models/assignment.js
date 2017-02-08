/*
Description: Add, Get, Set, Delete Assignments
*/

function AssignmentService($http, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._appSettings = appSettings;
    this.courseUserId = SessionService.getCourseUserId();
}

AssignmentService.prototype.getAssignments = function(courseId){
    var self = this;
    return this._$http
          .get(this._appSettings.API.basePath + '/rest/student/course/'+courseId+'/modules',
              {params : {
                  "courseUserId" : self.courseUserId
              }})
          .then(function (res) {
            console.log("Got Assignments List");
            console.log(res);
            return res.data;
          });
}

module.exports = angular.module('app.models.assignment', [
    'app.settings'
]).service('AssignmentService', AssignmentService);
