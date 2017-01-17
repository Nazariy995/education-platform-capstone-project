

function Controller($scope, $state, AuthService){
    "ngInject";
    
    this._AuthService = AuthService;

    this.credentials = {
        username: '',
        password : ''
    };
};

Controller.prototype.login = function(credentials){
    console.log(credentials);
    this._AuthService.login(credentials).then(function(res){
        console.log(res);
        //Hack for role switching
//            if(self.credentials.username == "student"){
//                $rootScope.role="student";
//            }else{
//                $rootScope.role="teacher";
//            }
//            //end of hack
//            if(res.authenticated){
//                $rootScope.authenticated = true;
//                $state.go("home");
//                console.log("Login Succeeded ")
//            }else{
//                $rootScope.authenticated = false;
//                $state.go("/");
//                console.log("Login Failed");
//            }
    },function(){
        console.log("failed");  
//        $rootScope.authenticated = false;
//            console.log("Login Failed");
    });
    
}


module.exports = angular.module('app.views.app.login.controller', [])
.controller('AppLoginController', Controller);

