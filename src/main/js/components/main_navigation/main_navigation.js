function Directive($state, appSettings, SessionService){
    "ngInject";

    function link(scope, element){

        var roles = appSettings.ROLES;

        var user = SessionService.getUser();
        if (user.roles.indexOf(roles.user) != -1){
            scope.navigationLinks = appSettings[roles.user]["mainNavigationLinks"];
        } else if(user.roles.indexOf(roles.instructor) != -1) {
            scope.navigationLinks = appSettings[roles.instructor]["mainNavigationLinks"];
        }


        scope.logout = function(){
            SessionService.destroy();
            $state.go('app.login');
            window.location.reload();
        }

    }

    var directive = {
        restrict: 'E',
        templateUrl: 'components/main_navigation/main_navigation.html',
        link: link
    }

    return directive;
}

module.exports =  Directive;
