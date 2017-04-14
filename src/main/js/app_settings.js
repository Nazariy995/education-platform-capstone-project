module.exports = angular.module('app.settings', [])
.constant("appSettings", {
    "ROLES" : {
        user : "USER",
        instructor : "INSTRUCTOR",
        admin : "ADMIN"
    },
    "API" : {
        "basePath" : "",
        "PARAMS" : {
            "courseUserId" : 'courseUserId'
        }
    },
    "QUESTION_TYPES" : {
        MULTIPLE_CHOICE : "Multiple Choice",
        NUMERIC : "Numeric",
        FREE_RESPONSE : "Free Response",
        IMAGE : "Image Upload",
        TEXT : "Front Text"
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
            }
        ],
        "quizComponentTemplates" : {
            "MULTIPLE_CHOICE" : "multiple_choice.html"
        }
    },
    "INSTRUCTOR" : {
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
            },
            {
                "name" : "Members",
                "state" : "app.course.members",
                "icon" : "glyphicon glyphicon-th"
            }
        ],
        "quizComponentTemplates" : {
            "MULTIPLE_CHOICE" : "multiple_choice.html"
        }
    }
})
