function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        setAnswer(scope);
    }

    function setAnswer( scope ){
        var _scope = scope;
        scope.$watch('savedAnswers', function(newAnswer){
            if(newAnswer && _scope.field){
                if("pointsEarned" in newAnswer || "comment" in newAnswer){
                    _scope.model = {};
                }
                if("pointsEarned" in newAnswer){
                    _scope.model.points = newAnswer.pointsEarned;
                };
                if("comment" in newAnswer){
                    _scope.model.comment = newAnswer.comment;
                }
            }
        });
    }

    var directive = {
        templateUrl: 'components/question_grading/points_comments/home.html',
        link : link,
        scope: {
            model: '=',
            savedAnswers : '=',
            field: '=',
            isGrading: '='
        }
    }

    return directive;
}

module.exports =  Directive;
