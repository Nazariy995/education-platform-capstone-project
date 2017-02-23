
function onStateChange($rootScope, $state, AuthService, SessionService){
    "ngInject";

    $rootScope.$on('$stateChangeStart', function(evt, toState, toParams, fromState, fromParams){
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
