module.exports = angular.module('app.settings', [])
.constant("appSettings", {
    "API" : {
        "basePath" : ""
    },
    "USER" : {
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
