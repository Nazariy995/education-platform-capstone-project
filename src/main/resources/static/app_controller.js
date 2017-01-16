

function AppController($scope, $rootScope){
    "ngInject";

    var controller= this;
    controller._$rootScope = $rootScope;
    controller._$rootScope.user = null;
    
}

module.exports = AppController;
