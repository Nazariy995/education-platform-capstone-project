/*
Description: Add, Get, Set, Delete Assignments
*/

function AssignmentService($http, appSettings, SessionService){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._appSettings = appSettings;
    this.courseUserIdValue = SessionService.getCourseUserId();
    this.courseUserIdKey = appSettings.API.PARAMS.courseUserId;
    this.init();
}

AssignmentService.prototype.init = function(){
    var self = this;
    self.params = {};
    self.params[self.courseUserIdKey] = self.courseUserIdValue;
    self.config = {
        params : self.params
    }
}

AssignmentService.prototype.getAssignments = function(courseId){
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/student/course/'+courseId+'/modules'
    return this._$http
          .get(url, self.config)
          .then(function (res) {
            console.log("Got Assignments List");
            console.log(res);
            return res.data;
          });
}

AssignmentService.prototype.getAssignmentDetails = function(courseId, moduleId){
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/student/course/'+ courseId+ '/module/' + moduleId;
    return self._$http
        .get(url, self.config)
        .then(function(res){
        console.log("Get Assignment Details");
        console.log(res);
        return res.data;
    })
};

AssignmentService.prototype.getAssignmentMembers = function(courseId, moduleId){
    var self = this;
    var url = self._appSettings.API.basePath + '/rest/student/course/'+ courseId+ '/module/' + moduleId + '/free';
    return self._$http
        .get(url, self.config)
        .then(function(res){
        console.log("Get Assignment Members");
        console.log(res);
        return res.data;
    })

}

module.exports = angular.module('app.models.assignment', [
    'app.settings'
]).service('AssignmentService', AssignmentService);
