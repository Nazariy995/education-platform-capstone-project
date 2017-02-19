/*
Description: Add, Get, Set, Delete Group Data
*/

function GroupService($http, appSettings, SessionService){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._appSettings = appSettings;
    this._SessionService = SessionService;
    this.courseUserIdKey = appSettings.API.PARAMS.courseUserId;
    this.init();
}

GroupService.prototype.init = function(){
    var self = this;
    self.getConfig();
};

GroupService.prototype.getConfig = function(){
    var self = this;
    self.params = {};
    self.params[self.courseUserIdKey] = self._SessionService.getCourseUserId();
    self.config = {
        params : self.params
    };
};

GroupService.prototype.getGroupMembers = function(courseId, moduleId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+ courseId+ '/module/' + moduleId + "/group"
    return self._$http
        .get(url, self.config)
        .then(function(res){
        console.log("Get Assignment Group Data")
        console.log(res)
        return res.data;
    });
}

GroupService.prototype.addGroupMember = function(courseId, moduleId, groupId, newMemberId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/member/"
        + newMemberId;
    console.log(url);
    url = encodeURI(url);
    console.log(url);
    console.log(self.config);
    return self._$http
        .post(url, null, self.config)
        .then(function(res){
            console.log("Add Member To Group");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.removeGroupMember = function(courseId, moduleId, groupId, memberToBeRemovedId){
    var self= this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/member/"
        +memberToBeRemovedId;
    return self._$http
        .delete(url, self.config)
        .then(function(res){
            console.log("Remove Member From Group");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.groupCheckin = function(courseId, moduleId, groupId, loginInfo){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/checkin";
    url = encodeURI(url);
    return self._$http
        .post(url, loginInfo, self.config)
        .then(function(res){
            console.log("Group Checkin");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.getLock = function(courseId, moduleId, groupId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/lock";
    url = encodeURI(url);
    return self._$http
        .get(url, self.config)
        .then(function(res){
            console.log("Get Group Lock");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.getCheckedIn = function(courseId, moduleId, groupId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/checkin";
    url = encodeURI(url);
    return self._$http
        .get(url, self.config)
        .then(function(res){
            console.log("Get Checked In");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.finalize = function(courseId, moduleId, groupId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/finalize";
    url = encodeURI(url);
    return self._$http
        .post(url, null, self.config)
        .then(function(res){
            console.log("Group Finalized");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.initialize = function(courseId, moduleId){
    var self = this;
    self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/";
    url = encodeURI(url);
    return self._$http
        .post(url, null, self.config)
        .then(function(res){
            console.log("Group Initialize");
            console.log(res);
        return res.data;
    });
}


module.exports = angular.module('app.models.group', [
    'app.settings'
]).service('GroupService', GroupService);
