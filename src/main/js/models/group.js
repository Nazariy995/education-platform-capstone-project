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
};

GroupService.prototype.getConfig = function(){
    var self = this;
    var params = {};
    params[self.courseUserIdKey] = self._SessionService.getCourseUserId();
    var config = {
        params : params
    };

    return config;
};

//Purpose: Get group members for a specific assignment
GroupService.prototype.getGroupMembers = function(courseId, moduleId){
    var self = this;
    var config = self.getConfig();

    var url = self._appSettings.API.basePath
    + '/rest/student/course/'
    + courseId+ '/module/'
    + moduleId + "/group";

    return self._$http
        .get(url, config)
        .then(function(res){
        return res.data;
    });
};

//Purpose: Get Assignment groups for the instructor to grade
GroupService.prototype.getAssignmentGroups = function(courseId, moduleId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath
    + '/rest/instructor/course/'
    + courseId + '/module/'
    + moduleId + "/groups"

    return self._$http
        .get(url, config)
        .then(function(res){
        console.log("Get Assignment Groups Data")
        console.log(res)
        return res.data;
    });
}

GroupService.prototype.addGroupMember = function(courseId, moduleId, groupId, newMemberId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/member/"
        + newMemberId;

    url = encodeURI(url);

    return self._$http
        .post(url, null, config)
        .then(function(res){
            console.log("Add Member To Group");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.removeGroupMember = function(courseId, moduleId, groupId, memberToBeRemovedId){
    var self= this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/member/"
        +memberToBeRemovedId;
    return self._$http
        .delete(url, config)
        .then(function(res){
            console.log("Remove Member From Group");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.groupCheckin = function(courseId, moduleId, groupId, loginInfo){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/checkin";
    url = encodeURI(url);
    return self._$http
        .post(url, loginInfo, config)
        .then(function(res){
            console.log("Group Checkin");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.groupCheckout = function(courseId, moduleId, groupId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/checkin-reset";
    url = encodeURI(url);
    return self._$http
        .post(url, null, config)
        .then(function(res){
            console.log("Group Reset");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.getLock = function(courseId, moduleId, groupId, pageNum){
    var self = this;
    var config = self.getConfig();
    config.params.page = pageNum;
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/canEdit";
    url = encodeURI(url);
    return self._$http
        .get(url, config)
        .then(function(res){
            console.log("Get Group Lock");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.getCheckedIn = function(courseId, moduleId, groupId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/checkin";
    url = encodeURI(url);
    return self._$http
        .get(url, config)
        .then(function(res){
            console.log("Get Checked In");
            console.log(res);
        return res.data;
    });
};

GroupService.prototype.finalize = function(courseId, moduleId, groupId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/"
        + groupId + "/finalize";
    url = encodeURI(url);
    return self._$http
        .post(url, null, config)
        .then(function(res){
            console.log("Group Finalized");
            console.log(res);
        return res.data;
    });
};

//Initialize the group
//Automatically add the first person to the group
GroupService.prototype.initialize = function(courseId, moduleId){
    var self = this;
    var config = self.getConfig();
    var url = self._appSettings.API.basePath + '/rest/student/course/'+
        courseId+ '/module/'
        + moduleId + "/group/";
    url = encodeURI(url);
    return self._$http
        .post(url, null, config)
        .then(function(res){
            console.log("Group Initialize");
            console.log(res);
        return res.data;
    });
}


module.exports = angular.module('app.models.group', [
    'app.settings'
]).service('GroupService', GroupService);
