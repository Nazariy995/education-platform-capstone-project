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
var appSettings = require('./app_settings');

angular.module("app", [
    //External
    'ui.router',

    //Internal
    appSettings.name,
    models.name,
    components.name,
    views.name
])
.controller('AppController', appController)
.config(appRouter);

},{"./app_controller":2,"./app_router":3,"./app_settings":4,"./components/module":5,"./models/module":10,"./views/module":15}],2:[function(require,module,exports){

function AppController($scope, $rootScope, AuthService, SessionService){
    "ngInject";
    this.auth = AuthService;
    this.session = SessionService;
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
            controller: 'LoginCtrl',
            controllerAs: 'login'
        },
        {
            name:'home',
            url:'/home',
            templateUrl:'views/app/home/home.html',
            controller: 'HomeCtrl',
            controllerAs: 'home'
        },
        {
            name:'home.teacher',
            url:'/teacher',
            templateUrl:'views/teacher/home/home.html',
            controller: 'Teacher.HomeCtrl',
            controllerAs: 'teacherHome'
        },
        {
            name:'home.student',
            url:'/student',
            templateUrl:'views/student/home/home.html',
            controller: 'Student.HomeCtrl',
            controllerAs: 'studentHome'
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
module.exports = angular.module('app.settings', [])
.constant("appSettings", {
    "API" : {
        "basePath" : "http://localhost:8080"
    }
})

},{}],5:[function(require,module,exports){

var services = require('./services/module');

module.exports = angular.module('app.components', [
    services.name
]);

},{"./services/module":7}],6:[function(require,module,exports){
var appSettings = require("app_settings");
var sessionService = require("./session_service");

function AuthService($http, $window, appSettings, SessionService){
    "ngInject";

    this._$http = $http;
    this._$window = $window;
    this._appSettings = appSettings;
    this._SessionService = SessionService;
}

AuthService.prototype.login = function(credentials){
    var self = this;
    var headers = credentials ? {authorization : "Basic "
                     + btoa(credentials.username + ":" + credentials.password)
                    } : {};

    return this._$http
              .get(this._appSettings.API.basePath+"/home", {headers : headers})
              .then(function (response) {
                    console.log(response);
                    var user = {
                        "first-name" : "Nazariy",
                        "last-name" : "Dumanskyy",
                        "roles" : [ "USER" ]
                               };
                    var accessToken = response.headers("x-auth-token");
                    self._SessionService.create(user, accessToken);
                    self._$http.defaults.headers.common['X-Auth-Token'] = accessToken;
                return user;
              });
}

AuthService.prototype.logout = function(){
    this._SessionService.destroy();
}

module.exports = angular.module('app.components.services.auth_service', [
    appSettings.name,
    sessionService.name
])
.service('AuthService', AuthService);

},{"./session_service":8,"app_settings":4}],7:[function(require,module,exports){


var authService = require('./auth_service');
var sessionService = require('./session_service');

module.exports = angular.module('app.components.services', [
    authService.name,
    sessionService.name
]);

},{"./auth_service":6,"./session_service":8}],8:[function(require,module,exports){


function SessionService($window){
    "ngInject";

    this._$window = $window;
    this._user = JSON.parse(localStorage.getItem('session.user'));
    this._accessToken = JSON.parse(localStorage.getItem('session.accessToken'));
}

SessionService.prototype.getUser = function(){
    return this._user;
}

SessionService.prototype.setUser = function(user){
    this._user = user;
    this._$window.localStorage.setItem('session.user', JSON.stringify(user));
    return this;
}

SessionService.prototype.getAccessToken = function(){
    return this._accessToken;
}

SessionService.prototype.setAccessToken = function(accessToken){
    this._accessToken = accessToken;
    this._$window.localStorage.setItem('serssion.accessToken', accessToken);
    return this;
}

SessionService.prototype.destroy = function(){
    this.setUser(null);
    this.setAccessToken(null);
}

SessionService.prototype.create = function(user, accessToken){
    this.setUser(user);
    this.setAccessToken = accessToken;
}

module.exports = angular.module('app.components.services.session_service', [])
    .service('SessionService', SessionService);




},{}],9:[function(require,module,exports){
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





},{}],10:[function(require,module,exports){


var courseModel = require('./course');
var navigationLinksModel = require('./navigation_link');

module.exports = angular.module('app.models', [
    courseModel.name,
    navigationLinksModel.name
])

},{"./course":9,"./navigation_link":11}],11:[function(require,module,exports){
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

},{}],12:[function(require,module,exports){

function Controller($scope, $state, SessionService) {
    "ngInject";

    var self = this;
    self._$scope = $scope;
    self._$scope.navigation_links = [];
    self.user_roles = SessionService.getUser().roles;

    if(self.user_roles.indexOf("USER") != -1){
        console.log("yes");
        $state.go("home.teacher");
    }

}

module.exports = angular.module('app.views.app.controller', [
    "app.components.services.session_service"
])
.controller('HomeCtrl', Controller);

},{}],13:[function(require,module,exports){


function Controller($scope, $state, AuthService){
    "ngInject";

    this._AuthService = AuthService;
    this._$state = $state;
    this._$scope = $scope;
    this._$scope.error = null;

    this.credentials = {
        username: '',
        password : ''
    };
};

Controller.prototype.login = function(credentials){
    var self = this;
    self._AuthService.login(credentials).then(function(response){
        console.log(response);
        self._$state.go("home");
    },function(err){
        console.log("failed");
        self._$scope.error = err;
    });

}


module.exports = angular.module('app.views.app.login.controller', [])
.controller('LoginCtrl', Controller);


},{}],14:[function(require,module,exports){

var homeController = require("./home/controller");
var loginController = require("./login/controller");


module.exports = angular.module('app.views.app', [
    homeController.name,
    loginController.name
]);

},{"./home/controller":12,"./login/controller":13}],15:[function(require,module,exports){
var viewsApp = require('./app/module');

module.exports = angular.module('app.views', [
    viewsApp.name
]);


},{"./app/module":14}]},{},[1]);
