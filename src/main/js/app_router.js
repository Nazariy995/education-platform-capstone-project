

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
        },
        {
            name : 'home.account',
            url : '/account',
            templateUrl : 'views/app/account/account.html',
            controller : 'AccountCtrl',
            controllerAs : 'account'
        }
    ]

    states.forEach(function(state) {
        $stateProvider.state(state);
    });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

//    Removing # from the urls
}

module.exports = Router;
