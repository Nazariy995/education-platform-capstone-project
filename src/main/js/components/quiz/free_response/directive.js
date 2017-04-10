function Directive($state){
    "ngInject";

    function link(scope, element, attributes){

        setAnswer(scope);

    }

    function setAnswer( scope ){
        var _scope = scope;
        scope.$watch('savedAnswers', function(newAnswer){
            if(newAnswer && "value" in newAnswer){
                _scope.model = {
                    type : _scope.field.questionType
                };
                _scope.model.answer = newAnswer.value.answer;
            }
        });
    }

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
        templateUrl: 'components/quiz/free_response/home.html',
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
