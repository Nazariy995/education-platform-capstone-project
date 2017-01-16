(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
'use strict';


//External
//require('angular');
//require('angular-ui-router');

//Internal
var views = require('./views/module');
var models = require('./models/module');
var components = require('./components/module');
var appController = require('./app_controller');
var appRouter = require('./app_router');

angular.module("app", [
    //External
    'ui.router',

    //Internal
    models.name,
    components.name,
    views.name
])
.controller('AppController', appController)
.config(appRouter);

},{"./app_controller":2,"./app_router":3,"./components/module":4,"./models/module":8,"./views/module":13}],2:[function(require,module,exports){


function AppController($scope, $rootScope){
    "ngInject";

    var controller= this;
    controller._$rootScope = $rootScope;
    controller._$rootScope.user = null;
    
}

module.exports = AppController;

},{}],3:[function(require,module,exports){


function Router($stateProvider, $httpProvider, $locationProvider){
    "ngInject";

    var states = [
        {
            name:'login',
            url:'/',
            templateUrl:'views/app/login/login.html',
            controller: 'AppLoginController',
            controllerAs: 'ctrl'
        },
        {
            name:'home',
            url:'/home',
            templateUrl:'views/app/home/home.html',
            controller: 'HomeCtrl',
            controllerAs: 'ctrl'
        },
        {
            name:'home.teacher',
            url:'/teacher',
            templateUrl:'views/teacher/home/home.html',
            controller: 'Teacher.HomeCtrl',
            controllerAs: 'TeacherHomeCtrl'
        },
        {
            name:'home.student',
            url:'/student',
            templateUrl:'views/student/home/home.html',
            controller: 'Student.HomeCtrl',
            controllerAs: 'StudentHomeCtrl'
        }
    ]

    states.forEach(function(state) {
        $stateProvider.state(state);
    });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    //Removing # from the urls
    $locationProvider.html5Mode(true);
}

module.exports = Router;

},{}],4:[function(require,module,exports){

var services = require('./services/module');

module.exports = angular.module('app.components', [
    services.name
]);

},{"./services/module":6}],5:[function(require,module,exports){

function AuthService($http, $window){
    "ngInject";

    this._$http = $http;
    this._$window = $window;
}

AuthService.prototype.login = function(credentials){
    var headers = credentials ? {authorization : "Basic "
                     + btoa(credentials.username + ":" + credentials.password)
                    } : {};

    return this._$http
              .get('http://localhost:8080', {headers : headers})
              .then(function (headers) {
//                console.log(resposnse.headers("x-auth-token"));  
                return headers;
              });


}

module.exports = angular.module('app.components.services.auth_service', [])
    .service('AuthService', AuthService);

},{}],6:[function(require,module,exports){


var authService = require('./auth_service');

module.exports = angular.module('app.components.services', [
    authService.name
]);

},{"./auth_service":5}],7:[function(require,module,exports){
/*
Description: Add, Get, Set, Delete Courses
*/

function CourseService($http){
    "ngInject";

    this._$http = $http;

}

CourseService.prototype.getCourses = function(){
    return this._$http
          .get('./test/courses.json')
          .then(function (res) {
            return res.data;
          });
}

module.exports = angular.module('app.models.course', [])
    .service('CourseService', CourseService);





},{}],8:[function(require,module,exports){


var courseModel = require('./course');
var navigationLinksModel = require('./navigation_link');

module.exports = angular.module('app.models', [
    courseModel.name,
    navigationLinksModel.name
])

},{"./course":7,"./navigation_link":9}],9:[function(require,module,exports){
/*
Description: Get Navigation Links
*/

function NavigationLinksService($http){
    "ngInject";

    this._$http = $http;

}

NavigationLinksService.prototype.getLinks = function(){
    return this._$http
          .get('./test/courses.json')
          .then(function (res) {
            return res.data;
          });
}

module.exports = angular.module('app.models.navigationLinks', [])
    .service('NavigationLinksService', NavigationLinksService);

},{}],10:[function(require,module,exports){

function Controller($scope,$state) {
    "ngInject";

    this._$scope = $scope;
    this._$scope.navigation_links = [];


}

module.exports = angular.module('app.views.app.controller', [])
.controller('AppHomeCtrl', Controller);

},{}],11:[function(require,module,exports){


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
        $rootScope.authenticated = false;
            console.log("Login Failed");
    });
    
}


module.exports = angular.module('app.views.app.login.controller', [])
.controller('AppLoginController', Controller);


},{}],12:[function(require,module,exports){

var homeController = require("./home/controller");
var loginController = require("./login/controller");


module.exports = angular.module('app.views.app', [
    homeController.name,
    loginController.name
]);

},{"./home/controller":10,"./login/controller":11}],13:[function(require,module,exports){
var viewsApp = require('./app/module');

module.exports = angular.module('app.views', [
    viewsApp.name
]);


},{"./app/module":12}]},{},[1]);
