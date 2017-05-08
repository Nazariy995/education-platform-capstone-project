function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        var _scope = scope;
        //set the answer if available
        setAnswer(_scope);

    }

    function setAnswer( scope ){
        var _scope = scope;
        _scope.$watch('savedAnswers', function(newAnswer){
            if(newAnswer && "value" in newAnswer){
                _scope.model = {
                    type : _scope.field.questionType
                };
                _scope.model.answer = newAnswer.value.answer;
            }
        });
    }

    var directive = {
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
