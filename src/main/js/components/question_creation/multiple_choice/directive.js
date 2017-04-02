function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        scope.model.pageItemType = "QUESTION";
        if(!("isGatekeeper" in scope.model)){
            scope.model.isGatekeeper = false;
        }

        if(!("options" in scope.model)){
            scope.model.options = []
        }

        scope.addNewOption = function(option){
            addOption(scope, option);
        }

        scope.removeOption = function(index){
            scope.model.options.splice(index,1);
        }

    }

    function addOption(scope, option){
        var newOption = {
            humanReadableText : option,
            isCorrectOption :  false
        };
        scope.model.options.push(newOption);
        scope.newOption = "";
    };

    var directive = {
        templateUrl: 'components/question_creation/multiple_choice/home.html',
        link : link,
        scope: {
            model: '='
        }
    }

    return directive;
}

module.exports =  Directive;
