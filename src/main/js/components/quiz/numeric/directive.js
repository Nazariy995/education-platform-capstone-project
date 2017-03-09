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
        templateUrl: 'components/quiz/numeric/home.html',
        link : link,
        scope: {
            model: '=',
            editable : '=',
            field: '='
        }
    }

    return directive;
}

module.exports =  Directive;
