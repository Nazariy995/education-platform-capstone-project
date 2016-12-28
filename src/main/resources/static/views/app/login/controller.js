var app = angular.module('hello');
app.controller('LoginCtrl', function($rootScope, $scope,$state, AuthService){
    var self = this;
    self.credentials = {
        username:'',
        password:''
    };

    self.login = function(credentials){
        AuthService.login(credentials).then(function(res){
            //Hack for role switching
            if(self.credentials.username == "student"){
                $rootScope.role="student";
            }else{
                $rootScope.role="teacher";
            }
            //end of hack
            if(res.authenticated){
                $rootScope.authenticated = true;
                $state.go("home");
                console.log("Login Succeeded ")
            }else{
                $rootScope.authenticated = false;
                $state.go("/");
                console.log("Login Failed");
            }
        },function(){
            $rootScope.authenticated = false;
                console.log("Login Failed");
        });
    };


});