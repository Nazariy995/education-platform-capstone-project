function Directive(){
    "ngInject";

    function link(scope, element, attr, modelCtrl){
        modelCtrl.$formatters.push(function(modelValue){
            return new Date(modelValue);
        })

    };

    var directive = {
        require : 'ngModel',
        link: link
    }

    return directive;
}

module.exports =  Directive;
