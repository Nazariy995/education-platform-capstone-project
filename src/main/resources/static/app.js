var app = angular.module('hello', [ 'ui.router' ]);

app.config(function($stateProvider, $httpProvider, $locationProvider) {
    var states = [
        {
            name:'login',
            url:'/',
            templateUrl:'views/app/login/login.html',
            controller: 'LoginCtrl',
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


	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
    //Removing # from the urls
    $locationProvider.html5Mode(true);

});
