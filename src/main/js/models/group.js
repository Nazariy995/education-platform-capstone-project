/*
Description: Add, Get, Set, Delete Group Data
*/

function GroupService($http, appSettings, SessionService){
    "ngInject";
    var self = this;
    this._$http = $http;
    this._appSettings = appSettings;
    this.courseUserIdValue = SessionService.getCourseUserId();
    this.courseUserIdKey = appSettings.API.PARAMS.courseUserId;
    this.init();
}

GroupService.prototype.init = function(){
    var self = this;
    self.params = {}
    self.params[self.courseUserIdKey] = self.courseUserIdValue;
    self.config = {
        params : self.params
    }
}

GroupService.prototype.getGroupMembers = function(courseId, moduleId){
    var self = this;
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

}

//GroupService.prototype.groupCheckin = function(courseId, moduleId, groupId){
//    var self = this;
//
//
//
//
//}


module.exports = angular.module('app.models.group', [
    'app.settings'
]).service('GroupService', GroupService);
