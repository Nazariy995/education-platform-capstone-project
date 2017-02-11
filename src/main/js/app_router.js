

function Router($stateProvider, $httpProvider, $locationProvider){
    "ngInject";

    var states = [
        {
            name : 'app',
            url : '/',
            views : {
                'app' : {
                    templateUrl : 'views/app/home/home.html'
                }
            }
        },
        {
            name : 'app.login',
            url : 'login',
            views : {
                'app@' : {
                    templateUrl : 'views/app/login/login.html',
                    controller: 'LoginCtrl',
                    controllerAs: 'login'
                }
            }
        },
        {
            name : 'app.account',
            url : 'account',
            views : {
                'mainContent@app' : {
                    templateUrl : 'views/app/account/account.html',
                    controller : 'AccountCtrl',
                    controllerAs : 'account'  
                }
            }
        }, 
        {
            name : 'app.courses',
            url : 'courses',
            views : {
                'mainContent@app' : {
                    templateUrl: 'views/student/home/home.html',
                    controller: 'Student.HomeCtrl',
                    controllerAs: 'studentHome'
                }
            }
        }, 
        {
            name : 'app.course',
            url  : 'courses/{courseId}',
            views : {
                'mainContent@app' : {
                    templateUrl : 'views/student/course/home.html',
                    controller : 'Student.Course',
                    controllerAs : 'course'
                }
            },
            resolve : {
                course : ['CourseService','SessionService','$stateParams', function(CourseService, SessionService, $stateParams){
                    return CourseService.getCourse($stateParams.courseId)
                        .then(function(course){
                        SessionService.setCourseUserId(course.courseUserId);
                        return course;
                    }, function(err){
                        return err;
                    });
                }]
            }
        },
        {
            name : 'app.course.assignments',
            url : '/assignments',
            views : {
                'childContent' : {
                    templateUrl : 'views/student/assignments/home.html',
                    controller : 'Student.AssignmentsCtrl',
                    controllerAs : 'courseAssignments'
                }
            }
        },
        {
            name : 'app.course.grades',
            url : '/grades',
            views : {
                'childContent' : {
                    templateUrl : 'views/student/grades/home.html',
                    controller : 'Student.GradesCtrl',
                    controllerAs : 'courseGrades'
                }
            }
        },
        {
            name : 'app.course.assignment',
            url : '/assignments/{moduleId}',
            views : {
                'childContent' : {
                    templateUrl : 'views/student/assignment/home.html',
                    controller : 'Student.AssignmentDetailsCtrl',
                    controllerAs : 'assignmentDetails'
                },
                'assignmentContent@app.course.assignment' : {
                    templateUrl : 'views/student/assignment_details/home.html'
                }
            }
        },
        {
            name : 'app.course.assignment.group',
            url : '/group/{groupId}',
            views : {
                'assignmentContent' : {
                    templateUrl : 'views/student/assignment_create_group/home.html',
                    controller : 'Student.AssignmentGroupCtrl',
                    controllerAs : 'assignmentGroup'

                }
            }
        },
        {
            name :  'app.course.assignment.login',
            url : '/login',
            views : {
                'assignmentContent' : {
                    templateUrl : 'views/student/assignment_group_login/home.html',
                    controller : 'Student.AssignmentLoginCtrl',
                    controllerAs : 'assignmentLogin'
                }
            }
        }




    ]

    states.forEach(function(state) {
        $stateProvider.state(state);
    });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

//    Removing # from the urls
}

module.exports = Router;
