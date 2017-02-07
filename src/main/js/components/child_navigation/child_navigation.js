
function Directive($state, appSettings, SessionService){
    "ngInject";

    function link(scope, element){

        var roles = SessionService.getUser().roles;
        if (roles.indexOf("USER") != -1){
            var role = "USER";
            scope.navigationLinks = appSettings[role]["childNavigationLinks"];
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
