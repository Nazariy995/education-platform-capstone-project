function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        var _scope = scope;
        scope.model = {
            type : scope.field.questionType
        };
        //set the answer if available
        setAnswer(scope);

    }

    function setAnswer( scope ){
        var _scope = scope;
        scope.$watch('savedAnswers', function(newAnswer){
            if(newAnswer && "value" in newAnswer){
                _scope.model.answer = newAnswer.value.answer;
            }
        });
    }

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
        templateUrl: 'components/quiz/multiple_choice/home.html',
        link : link,
        scope: {
            model: '=',
            savedAnswers : '=',
            editable : '=',
            field: '='
        }
    }

    return directive;
}

module.exports =  Directive;
