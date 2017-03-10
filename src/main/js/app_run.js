
function onStateChange($rootScope, $state, AuthService, SessionService, GroupService){
    "ngInject";

    $rootScope.$on('$stateChangeStart', function(evt, toState, toParams, fromState, fromParams){
        if(fromState
            && fromState.name == 'app.course.assignment.questions'
            && toState.name != fromState.name){
            console.log("Checkout");
            GroupService.groupCheckout(fromParams.courseId, fromParams.moduleId, fromParams.groupId)
        }

        //redirect to default state, the courses page
        if(toState.redirectTo){
            evt.preventDefault();
            $state.go(toState.redirectTo, toParams, {location: 'replace'});
        }


        //If the user has previously been authenticated
        if(SessionService.getUser()){
            if(toState.name == 'app.login'){
                AuthService.isAuthenticated().then(
                    function(response){
                        $state.go('app.courses');
                    }, function(err){
                        SessionService.destroy();
                    });
            }
        //if the user has not been previosuly authenticated
        } else if(!SessionService.getUser()) {
            if(toState.name != 'app.login'){
                evt.preventDefault();
                $state.go('app.login');
            }
        }
    })
}

module.exports = onStateChange;
