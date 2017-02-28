function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        scope.model = {
            type : scope.field.questionType
        };

    }

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
        templateUrl: 'components/quiz/multiple_choice/home.html',
        link : link,
        scope: {
            model: '=',
            field: '='
        }
    }

    return directive;
}

module.exports =  Directive;
