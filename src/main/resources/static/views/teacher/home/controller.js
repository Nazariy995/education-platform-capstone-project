var app = angular.module('hello');
app.controller('Teacher.HomeCtrl', function($rootScope, $scope, CourseService){
    $scope.courses = [];
    CourseService.get().then(function(res){
        $scope.courses = res;
    })

});
