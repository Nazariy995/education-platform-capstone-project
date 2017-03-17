
function Directive($state, appSettings, SessionService){
    "ngInject";

    function link(scope, element){

        var roles = appSettings.ROLES;

        var user = SessionService.getUser();
        if (user.roles.indexOf(roles.user) != -1){
            scope.navigationLinks = appSettings[roles.user]["childNavigationLinks"];
        } else if(user.roles.indexOf(roles.instructor) != -1) {
            scope.navigationLinks = appSettings[roles.instructor]["childNavigationLinks"];
        }

    }

    var directive = {
        restrict: 'E',
        templateUrl: 'components/child_navigation/child_navigation.html',
        link: link
    }

    return directive;
}

module.exports =  Directive;
