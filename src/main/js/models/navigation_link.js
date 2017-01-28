/*
Description: Get Navigation Links
*/

function NavigationLinksService($http){
    "ngInject";

    this._$http = $http;

}

NavigationLinksService.prototype.getLinks = function(){
    return this._$http
          .get('./test/courses.json')
          .then(function (res) {
            return res.data;
          });
}

module.exports = angular.module('app.models.navigationLinks', [])
    .service('NavigationLinksService', NavigationLinksService);
