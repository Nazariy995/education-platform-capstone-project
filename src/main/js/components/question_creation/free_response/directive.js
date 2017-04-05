function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        scope.model.pageItemType = "QUESTION";

    }


    var directive = {
        templateUrl: 'components/question_creation/free_response/home.html',
        link : link,
        scope: {
            model: '=',
        }
    }

    return directive;
}

module.exports =  Directive;
