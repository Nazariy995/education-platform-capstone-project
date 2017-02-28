function Directive($state){
    "ngInject";

    function link(scope, element, attributes){
        scope.model = {
            type : "Numeric"
        };

    }

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
        templateUrl: 'components/quiz/numeric/home.html',
        link : link,
        scope: {
            model: '='
        }
    }

    return directive;
}

module.exports =  Directive;
