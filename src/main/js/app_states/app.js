var states = [
    {
        name : 'app',
        url : '/',
        views : {
            'app' : {
                templateUrl : 'views/app/home/home.html'
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
    }
]

module.exports =  states;
