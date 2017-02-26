function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        scope.model = {
            type : "MULTIPLE_CHOICE"
        };

    }

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
        templateUrl: 'components/quiz/multiple_choice.html',
        link : link,
        scope: {
            model: '='
        }
    }

    return directive;
}

module.exports =  Directive;
