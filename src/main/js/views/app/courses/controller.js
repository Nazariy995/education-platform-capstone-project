
function Controller($state, $stateParams, CourseService){
    "ngInject";
    this._$state = $state;
    this.pageName = "Courses";
    this._CourseService = CourseService;
    this.created_updated = $stateParams.created_updated;
    this.init();
};

Controller.prototype.init = function(){
    var self = this;
    self.getCourses();
}

Controller.prototype.getCourses = function(){
    var self = this;
    self._CourseService.getCourses().then(
        function(payload){
        self.courses = payload;
    }, function(err){
        self.error = "ERROR retrieving courses";
    });
}

module.exports = angular.module('app.views.app.courses.controller', [ ])
.controller('CoursesCtrl', Controller);

