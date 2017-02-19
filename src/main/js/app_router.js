var app_states = require("./app_states/app");
var assignment_states = require("./app_states/assignment");
var course_states = require("./app_states/course");

function Router($stateProvider, $httpProvider, $locationProvider){
    "ngInject";

    //A fix for angular  1.6.1 because the hashPrefix got changed to ! so we needed to chane thay back
    $locationProvider.hashPrefix('');

    var states = []
    //Concatinate all of the states from app states folder
    states = states.concat(app_states);
    states = states.concat(assignment_states);
    states = states.concat(course_states);

    states.forEach(function(state) {
        $stateProvider.state(state);
    });


    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

//    Removing # from the urls
}

module.exports = Router;
