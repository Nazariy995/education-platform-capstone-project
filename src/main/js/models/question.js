/*
Description: Add, Get, Post questions to assignments
*/

function Service($http, appSettings, SessionService, Upload){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._Upload = Upload;
    this._appSettings = appSettings;
    this.userRoles = appSettings.ROLES;
    this._SessionService = SessionService;
    this.courseUserIdKey = appSettings.API.PARAMS.courseUserId;
};

Service.prototype.getConfig = function(){
    var self = this;
    var params = {};
    params[self.courseUserIdKey] = self._SessionService.getCourseUserId();
    var config = {
        params : params
    };
    return config
};

Service.prototype.getQuestions = function(courseId, moduleId, pageNumber){
    var self = this;
    var config = self.getConfig();

    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath
    + '/rest/student/course/'+ courseId +'/module/' + moduleId
    + '?page=' + pageNumber;
    url[self.userRoles.instructor] = self._appSettings.API.basePath
    + '/rest/instructor/course/'+ courseId +'/module/' + moduleId
    + '?page=' + pageNumber;
    url = self.getUrl(url);

    return this._$http
          .get(url, config)
          .then(function (res) {
            return res.data;
          });
};

//Purpose: Add a new question to the assignment
//Params: courseId - String, moduleId - String, pageNum - string
Service.prototype.addQuestion = function(courseId, moduleId, pageNum, payload){
    var self = this;
    var config = self.getConfig();
    config.params.page = pageNum;

    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/' + courseId
    +'/module/' + moduleId;

    return this._$http
          .post(url, payload, config)
          .then(function (res) {
            return res.data;
          });
};

//Purpose: Drop a question from the assignment
//Params: courseId - String, moduleId - String, itemId = String
Service.prototype.dropQuestion = function(courseId, moduleId, itemId){
    var self = this;
    var config = self.getConfig();

    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/' + courseId
    +'/module/' + moduleId
    + '/item/' + itemId;

    return this._$http
          .delete(url, config)
          .then(function (res) {
            return res.data;
          });
};

//Purpose: Add a new page to the assignment
//Params: courseId - String, moduleId - String
Service.prototype.addPage = function(courseId, moduleId){
    var self = this;
    var config = self.getConfig();

    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/' + courseId
    +'/module/' + moduleId
    +'/add-page';

    return this._$http
          .post(url, null, config)
          .then(function (res) {
            return res.data;
          });
};

//Purpose: Drop a certain page of the assignment
//Params: courseId - String, moduleId - String, pageNum - number
Service.prototype.dropPage = function(courseId, moduleId, pageNum){
    var self = this;
    var config = self.getConfig();
    config.params.page = pageNum;

    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/' + courseId
    +'/module/' + moduleId ;

    return this._$http
          .delete(url, config)
          .then(function (res) {
            return res.data;
          });
};

Service.prototype.saveAnswers = function(courseId, moduleId, groupId, payload){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/save";
    return this._$http
        .post(url, payload, config)
        .then(function (res) {
            console.log("Save all the answers");
            console.log(res.data);
            return res.data;
        });
};

Service.prototype.savePoints = function(courseId, moduleId, groupId, payload){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/instructor/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId;
    return this._$http
        .post(url, payload, config)
        .then(function (res) {
            console.log("Save all the answers");
            console.log(res.data);
            return res.data;
        });
}

Service.prototype.getAnswers = function(courseId, moduleId, groupId, showSaved){
    var self = this;
    var config = self.getConfig();
    config.params.showSaved = showSaved;

    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath + '/rest/student/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/";
    url[self.userRoles.instructor] = self._appSettings.API.basePath + '/rest/instructor/course/'+
    courseId+ '/module/'
    + moduleId + "/group/"
    + groupId + "/answers/";
    url = self.getUrl(url);

    return this._$http
        .get(url, config)
        .then(function (res) {
            console.log("Get all answers");
            console.log(res.data);
            return res.data;
        });
};

Service.prototype.uploadImage = function(file){
    var self = this;
    var config = self.getConfig();

    var url = {};
    url[self.userRoles.user] = self._appSettings.API.basePath
    + 'rest/student/upload';
    url[self.userRoles.instructor] = self._appSettings.API.basePath
    + 'rest/instructor/upload';
    url = self.getUrl(url);

    var data = {};
    data["file"] = file;
    return self._Upload.upload({ url: url, data : data })
          .then(function (res) {
            console.log("Uploaded Image");
            console.log(res);
            return res.data;
          });
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

module.exports = angular.module('app.models.question', [
    'app.settings'
]).service('QuestionService', Service);
