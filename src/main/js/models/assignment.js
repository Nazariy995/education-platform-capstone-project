/*
Description: Add, Get, Set, Delete Assignments
*/

function Service($http, appSettings, SessionService){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
    this.courseUserIdKey = appSettings.API.PARAMS.courseUserId;
    this.assignmentDetails = {};
    this.init();
}

Service.prototype.init = function(){
    var self = this;
    self.getConfig();
};

Service.prototype.getConfig = function(){
    var self = this;
    self.params = {};
    self.params[self.courseUserIdKey] = self._SessionService.getCourseUserId();
    self.config = {
        params : self.params
    };
};

//Purpose: Create a new assignment
//Params: courseId - String, payload - json/assignment details
Service.prototype.addAssignment = function(courseId, payload){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/instructor/course/'+courseId+'/module'
    return this._$http
          .post(url, payload, self.config)
          .then(function (res) {
            console.log("Add Assignment");
            console.log(res);
            return res.data;
          });
};

//Purpose: Edit current assignment details
//Params: courseId - String, moduleId - String, payload - json/assignment details
Service.prototype.editAssignment = function(courseId, moduleId, payload){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/instructor/course/'+courseId+ '/module/' + moduleId;
    return this._$http
          .put(url, payload, self.config)
          .then(function (res) {
            console.log("Edit Assignment");
            console.log(res);
            return res.data;
          });
}

Service.prototype.getAssignments = function(courseId){
    var self = this;
    self.getConfig();

    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/course/'+courseId+'/modules';
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/course/'+courseId+'/modules';
    url = self.getUrl(url);

    return this._$http
          .get(url, self.config)
          .then(function (res) {
            console.log("Got Assignments List");
            console.log(res);
            return res.data;
          });
}

//Get Assignment details
Service.prototype.getAssignmentDetails = function(courseId, moduleId){
    var self = this;
    self.getConfig();
    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/course/'+ courseId+ '/module/' + moduleId;
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/course/'+ courseId+ '/module/' + moduleId;
    url = self.getUrl(url);
//    self.config.cache = true;
    return self._$http
        .get(url, self.config)
        .then(function(res){
        return res.data;
    })
};

Service.prototype.getAssignmentMembers = function(courseId, moduleId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+ courseId+ '/module/' + moduleId + '/free';
    return self._$http
        .get(url, self.config)
        .then(function(res){
        console.log("Get Assignment Members");
        console.log(res);
        return res.data;
    })

};

//Retrieve the correct API URL based on the user role
Service.prototype.getUrl = function(url){
    var self = this;
    var user = self._SessionService.getUser();
    if(user.roles.indexOf(self.userRoles.user) != -1){
        return url[self.userRoles.user];
    } else if (user.roles.indexOf(self.userRoles.instructor) != -1){
        return url[self.userRoles.instructor];
    }
};


module.exports = angular.module('app.models.assignment', [
    'app.settings'
]).service('AssignmentService', Service);
