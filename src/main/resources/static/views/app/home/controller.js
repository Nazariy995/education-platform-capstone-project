var app = angular.module('hello');
app.controller('HomeCtrl', function($scope, $rootScope,$state, UserService, NavigationLinksService){
    $scope.navigation_links = [];
    UserService.get().then(function(res){
        //Hack to test the user role
        res.role = $rootScope.role;
        //end of a hack
        $rootScope.user = res;
        if(res.role=="teacher"){
            $state.go('home.teacher');
        }else if(res.role =='student'){
            $state.go('home.student');
        }
    });

    NavigationLinksService.get().then(function(res){
        $scope.navigation_links = res;
    });

















});
