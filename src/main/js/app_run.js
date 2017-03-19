
function onStateChange($rootScope, $state, $q, AuthService, SessionService, GroupService, AuthService){
    "ngInject";

    $rootScope.$on('$stateChangeStart', function(evt, toState, toParams, fromState, fromParams){
        if(SessionService.getUser()){
            if(fromState
                && fromState.name == 'app.course.assignment.questions'
                && toState.name != fromState.name){
                GroupService.groupCheckout(fromParams.courseId, fromParams.moduleId, fromParams.groupId)
            }

            //redirect to default state, the courses page
            if(toState.redirectTo){
                evt.preventDefault();
                $state.go(toState.redirectTo, toParams, {location: 'replace'});
            }
            console.log("inside state change");

        //If the user has previously been authenticated

            if(toState.name == 'app.login'){
                console.log("commented out section")
                AuthService.isAuthenticated().then(
                    function(response){
                        $state.go('app.courses');
                    }, function(err){
                        SessionService.destroy();
                    });
            }
        //if the user has not been previosuly authenticated
        }
        else if(!SessionService.getUser()) {
            console.log("Inside state change for not logged in ");
            if(toState.name != 'app.login' ){
                evt.preventDefault();
                AuthService.logout();
            }
        }
    });

    $rootScope.$on('$stateChangeError', function (evt, toState, toParams, fromState, fromParams, error) {
        console.log(error);
    });
}

module.exports = onStateChange;
