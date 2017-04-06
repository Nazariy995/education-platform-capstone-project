function Directive(){
    "ngInject";

    function link(scope, element, attr, ngModel){

        ngModel.$validators.compareTo = function(newPassword) {
          return newPassword == scope.confirmationPassword;
        };

        scope.$watch("confirmationPassword", function() {
          ngModel.$validate();
        });

    };

    var directive = {
        require : 'ngModel',
        link: link,
        scope : {
            confirmationPassword : "=compareTo"
        }
    }

    return directive;
}

module.exports =  Directive;
