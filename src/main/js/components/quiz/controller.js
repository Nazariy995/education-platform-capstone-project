
function Controller($scope, $attrs){
    "ngInject";
    var directiveScope = $scope.$parent;
    this.data = directiveScope.$eval($attrs.field);
}

module.exports = Controller;
