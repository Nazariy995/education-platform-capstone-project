module.exports = angular.module('app.settings', [])
.constant("appSettings", {
    "API" : {
        "basePath" : "",
        "PARAMS" : {
            "courseUserId" : 'courseUserId'
        }
    },
    "USER" : {
        "mainNavigationLinks" : [
            {
                "name" : "Courses",
                "url" : "home/student",
                "state" : "app.courses",
                "icon" : "glyphicon glyphicon-book"
            },
            {
                "name" : "Account",
                "url" : "home/account",
                "state" : "app.account",
                "icon" : "glyphicon glyphicon-user"
            }
        ],
        "childNavigationLinks" : [
            {
                "name" : "Assignments",
                "state" : "app.course.assignments",
                "icon" : "glyphicon glyphicon-edit"
            },
            {
                "name" : "Grades",
                "state" : "app.course.grades",
                "icon" : "glyphicon glyphicon-book"
            }
        ]
    }
})
