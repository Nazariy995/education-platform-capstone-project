function Directive($state){
    "ngInject";

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
        templateUrl: 'components/quiz/multiple_choice.html',
        scope: {
            model: '='
        }
    }

    return directive;
}

module.exports =  Directive;
