module.exports = angular.module('app.settings', [])
.constant("appSettings", {
    "API" : {
        "basePath" : "http://localhost:8080/rest"
    },
    "STUDENT" : {
        "mainNavigationLinks" : [
            {
                "name" : "Courses",
                "url" : "home/student",
                "state" : "home.student"
            },
            {
                "name" : "Account",
                "url" : "home/account",
                "state" : "home.account"
            }
        ]
    }
})
