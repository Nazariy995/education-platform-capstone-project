var app_states = require("./app_states/app");
var assignment_states = require("./app_states/assignment");
var course_states = require("./app_states/course");
var admin_states = require("./app_states/admin");

function Router($stateProvider, $httpProvider, $locationProvider, $urlRouterProvider){
    "ngInject";

    //A fix for angular  1.6.1 because the hashPrefix got changed to ! so we needed to chane thay back
    $locationProvider.html5Mode(false).hashPrefix('');

    var states = []
    //Concatinate all of the states from app states folder
    states = states.concat(app_states);
    states = states.concat(assignment_states);
    states = states.concat(course_states);
    states = states.concat(admin_states);

    states.forEach(function(state) {
        $stateProvider.state(state);
    });

    $urlRouterProvider.otherwise("/");
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

}

module.exports = Router;
