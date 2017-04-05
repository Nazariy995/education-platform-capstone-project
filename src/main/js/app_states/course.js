var states = [
    {
        name : 'app.course.grades',
        url : '/grades',
        views : {
            'childContent' : {
                templateUrl : 'views/app/course_grades/home.html',
                controller : 'CourseGrades',
                controllerAs : 'courseGradesCtrl'
            }
        }
    },
    {
        name : 'app.courses.add_edit',
        url : '/{courseId}/add_edit',
        views : {
            'mainContent@app' : {
                templateUrl : 'views/instructor/courses_add_edit/home.html',
                controller : 'Instructor.CoursesAddEdit',
                controllerAs : 'coursesEdit'
            }
        },
        resolve : {
            course : ['CourseService','SessionService','$stateParams', '$state', function(CourseService, SessionService, $stateParams, $state){
                if($stateParams.courseId == "new"){
                    return null
                } else {
                    return CourseService.getCourse($stateParams.courseId)
                        .then(function(course){
                        return course;
                    }, function(err){
                        $state.go('app.courses', null, { reload : true, location : 'replace'});
                        return err;
                    });
                }
            }]
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
        params : {
            created_updated : false
        },
        views : {
            'childContent' : {
                templateUrl : 'views/app/assignments/home.html',
                controller : 'AssignmentsCtrl',
                controllerAs : 'courseAssignments'
            }
        }
    },
    {
        name : 'app.course.members',
        url : '/members',
        views : {
            'childContent' : {
                templateUrl : 'views/instructor/course_members/home.html',
                controller : 'Instructor.CourseMembers',
                controllerAs : 'courseMembersCtrl'
            }
        }
    },
    {
        name : 'app.course.members_add',
        url : '/members/add',
        views : {
            'childContent' : {
                templateUrl : 'views/instructor/course_members_add/home.html',
                controller : 'Instructor.CourseMembersAdd',
                controllerAs : 'courseMembersAddCtrl'
            }
        }
    }
]

module.exports = states;
