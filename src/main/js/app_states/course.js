var states = [
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
    }
]

module.exports = states;
