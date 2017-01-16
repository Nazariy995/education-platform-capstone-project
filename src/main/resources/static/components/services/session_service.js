var app = angular.module('hello');
app.service('Session', function(){
    this.create = function(email, firstName, lastName, userRoles){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoles = userRoles;
    };

    this.destroy = function(){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRoles = userRoles;
    }
});
