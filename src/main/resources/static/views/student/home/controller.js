var app = angular.module('hello');
app.controller('Student.HomeCtrl', function($rootScope, $scope, CourseService){
    $scope.courses = [];
    CourseService.get().then(function(res){
        $scope.courses = res;
    })

});
