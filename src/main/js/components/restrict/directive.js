function Directive(SessionService){
    "ngInject";

    function compile(element, attr, linker){
        var accessDenied = true;
        var user = SessionService.getUser();

        var attributes = attr.access.split(" ");

        attributes.forEach(function(attr){
            if(user.roles.indexOf(attr) != -1){
                accessDenied = false;
            }
        })

        if(accessDenied){
            element.children().remove();
            element.remove();
        }

    };

    var directive = {
        restrict: 'A',
        scope : false,
        priority : 10000,
        compile: compile
    }

    return directive;
}

module.exports =  Directive;
