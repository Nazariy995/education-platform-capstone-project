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
                "state" : "home.student",
                "icon" : "glyphicon glyphicon-book"
            },
            {
                "name" : "Account",
                "url" : "home/account",
                "state" : "home.account",
                "icon" : "glyphicon glyphicon-user"
            }
        ]
    }
})
