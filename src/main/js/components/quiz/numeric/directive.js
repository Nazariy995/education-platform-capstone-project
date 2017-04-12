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
                _scope.model.unit = newAnswer.value.unit;
            }
        });
    }

    var directive = {
        templateUrl: 'components/quiz/numeric/home.html',
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
