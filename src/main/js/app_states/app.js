var states = [
    {
        name : 'app',
        url : '/',
        views : {
            'app' : {
                templateUrl : 'views/app/home/home.html',
            }
        },
        redirectTo : 'app.courses'
    },
    {
        name : 'app.login',
        url : 'login',
        params : {
            sessionExpired : false
        },
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
        params : {
            created_updated : false
        },
        views : {
            'mainContent@app' : {
                templateUrl: 'views/app/courses/home.html',
                controller: 'CoursesCtrl',
                controllerAs: 'coursesCtrl'
            }
        }
    }
]

module.exports =  states;
