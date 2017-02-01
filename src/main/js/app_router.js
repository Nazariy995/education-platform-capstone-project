

function Router($stateProvider, $httpProvider, $locationProvider){
    "ngInject";

    var states = [
        {
            name : 'app',
            url : '/',
            views : {
                'app' : {
                    templateUrl : 'views/app/home/home.html'
                },
                'mainNavigation@app' : {
                    templateUrl : 'views/app/mainNavigation/home.html'
                }
                
            }
        },
        {
            name : 'app.login',
            url : 'login',
            views : {
                'app@' : {
                    templateUrl : 'views/app/login/login.html',
                    controller: 'LoginCtrl',
                    controllerAs: 'login'
                }
            }
        },
        {
            name : 'app.account',
            url : 'account',
            views : {
                'mainContent@app' : {
                    templateUrl : 'views/app/account/account.html',
                    controller : 'AccountCtrl',
                    controllerAs : 'account'  
                }
            }
        }, 
        {
            name : 'app.courses',
            url : 'courses',
            views : {
                'mainContent@app' : {
                    templateUrl: 'views/student/home/home.html',
                    controller: 'Student.HomeCtrl',
                    controllerAs: 'studentHome' 
                }
            }
        }, 
        {
            name : 'app.course',
            url  : 'courses/{courseId}',
            views : {
                'mainContent@app' : {
                    templateUrl : 'views/student/course/home.html'
                },
                'childNavigation@app.course' : {
                    templateUrl : 'views/student/child_navigation/home.html'
                }
            }
            
        }
        
//        {
//            name:'home',
//            url:'/home',
//            templateUrl:'views/app/home/home.html',
//            controller: 'HomeCtrl',
//            controllerAs: 'home'
//        },
//        {
//            name:'home.teacher',
//            url:'/teacher',
//            templateUrl:'views/teacher/home/home.html',
//            controller: 'Teacher.HomeCtrl',
//            controllerAs: 'teacherHome'
//        },
//        {
//            name:'home.student',
//            url:'/student',
//            templateUrl:'views/student/home/home.html',
//            controller: 'Student.HomeCtrl',
//            controllerAs: 'studentHome'
//        },
//        {
//            name : 'home.account',
//            url : '/account',
//            templateUrl : 'views/app/account/account.html',
//            controller : 'AccountCtrl',
//            controllerAs : 'account'
//        },
//        {
//            name : 'home.assignments',
//            url :  "/course/{courseId}/assignments",
//            templateUrl : 'views/student/assignment/home.html',
//            controller : 'Student.AssignmentCtrl',
//            controllerAs : 'courseAssignments'
//
//        }
    ]

    states.forEach(function(state) {
        $stateProvider.state(state);
    });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

//    Removing # from the urls
}

module.exports = Router;
