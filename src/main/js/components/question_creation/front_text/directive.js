function Directive($state, QuestionService){
    "ngInject";

    function link(scope, element, attributes){
        scope.model.pageItemType = "TEXT";

    }


    var directive = {
        templateUrl: 'components/question_creation/front_text/home.html',
        link : link,
        scope: {
            model: '=',
        }
    }

    return directive;
}

module.exports =  Directive;
