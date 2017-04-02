function Directive($state, QuestionService){
    "ngInject";

    function link(scope, element, attributes){
        scope.model.pageItemType = "QUESTION";

    }


    var directive = {
        templateUrl: 'components/question_creation/image_upload/home.html',
        link : link,
        scope: {
            model: '=',
        }
    }

    return directive;
}

module.exports =  Directive;
