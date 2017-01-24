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

},{"./app_controller":2,"./app_router":3,"./app_settings":4,"./components/module":7,"./models/module":12,"./views/module":17}],2:[function(require,module,exports){

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
//    $locationProvider.html5Mode(true);
}

module.exports = Router;

},{}],4:[function(require,module,exports){
module.exports = angular.module('app.settings', [])
.constant("appSettings", {
    "API" : {
        "basePath" : "http://localhost:8080/rest"
    }
})

},{}],5:[function(require,module,exports){


function ChildNavigationDirective( ){






    var directive = {
        restrict: 'A',
        templateUrl: 'components/child_navigation/child_navigation.html'
    }

    return directive;
}

module.exports =  ChildNavigationDirective;

},{}],6:[function(require,module,exports){

var childNavigationDirective = require('./child_navigation.js');

module.exports = angular.module('app.components.child_navigation', [])
.directive('childNavigation', function() {
    return new childNavigationDirective();
})



},{"./child_navigation.js":5}],7:[function(require,module,exports){

var services = require('./services/module');
var childNavigation = require('./child_navigation/module');

module.exports = angular.module('app.components', [
    services.name,
    childNavigation.name
]);

},{"./child_navigation/module":6,"./services/module":9}],8:[function(require,module,exports){
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

    console.log(headers);
    return this._$http
              .get(this._appSettings.API.basePath+"/self", {headers : headers})
              .then(function (response) {
                    console.log(response);
                    var user = response.data;
                    var accessToken = response.headers("x-auth-token");
                    user = self._SessionService.create(user, accessToken);
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

},{"./session_service":10,"app_settings":4}],9:[function(require,module,exports){


var authService = require('./auth_service');
var sessionService = require('./session_service');

module.exports = angular.module('app.components.services', [
    authService.name,
    sessionService.name
]);

},{"./auth_service":8,"./session_service":10}],10:[function(require,module,exports){


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
    var tempUser = {
        "email" : user.email,
        "firstName" : user.firstName,
        "lastName" : user.lastName,
        "roles" : user.appRoles
    }
    this.setUser(tempUser);
    this.setAccessToken = accessToken;
    return tempUser;
}

module.exports = angular.module('app.components.services.session_service', [])
    .service('SessionService', SessionService);




},{}],11:[function(require,module,exports){
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





},{}],12:[function(require,module,exports){


var courseModel = require('./course');
var navigationLinksModel = require('./navigation_link');

module.exports = angular.module('app.models', [
    courseModel.name,
    navigationLinksModel.name
])

},{"./course":11,"./navigation_link":13}],13:[function(require,module,exports){
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

},{}],14:[function(require,module,exports){

function Controller($scope, $state, SessionService) {
    "ngInject";

    var self = this;
    self._$scope = $scope;
    self._$scope.navigation_links = [];
//    self.user_roles = SessionService.getUser().roles;
    $state.go('home.student');

//


//    if(self.user_roles.indexOf("USER") != -1){
//        console.log("yes");
//        $state.go("home.teacher");
//    }

}

module.exports = angular.module('app.views.app.controller', [
    "app.components.services.session_service"
])
.controller('HomeCtrl', Controller);

},{}],15:[function(require,module,exports){


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

Controller.prototype.login = function(){
    var self = this;
    self._AuthService.login(self.credentials).then(function(user){

        if(user.roles.indexOf("USER") != -1){
            self._$state.go("home.student");
        } else if(user.roles.indexOf("INSTRUCTOR") != -1) {
            self._$state.go("home.teacher");
        }
    },function(err){
        console.log("failed");
        self._$scope.error = err;
    });

}


module.exports = angular.module('app.views.app.login.controller', [])
.controller('LoginCtrl', Controller);


},{}],16:[function(require,module,exports){

var homeController = require("./home/controller");
var loginController = require("./login/controller");


module.exports = angular.module('app.views.app', [
    homeController.name,
    loginController.name
]);

},{"./home/controller":14,"./login/controller":15}],17:[function(require,module,exports){
var viewsApp = require('./app/module');
var studentViews = require('./student/module');

module.exports = angular.module('app.views', [
    viewsApp.name,
    studentViews.name
]);


},{"./app/module":16,"./student/module":19}],18:[function(require,module,exports){

function Controller($scope, $state, SessionService){
    "ngInject";
    this._$state = $state;
    this._$scope = $scope;
    this._$scope.courses = [];
};

module.exports = angular.module('app.views.student.home.controller', [])
.controller('Student.HomeCtrl', Controller);


},{}],19:[function(require,module,exports){

var homeController = require("./home/controller");

module.exports = angular.module('app.views.student', [
    homeController.name
]);

},{"./home/controller":18}]},{},[1]);
