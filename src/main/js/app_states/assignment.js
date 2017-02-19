var states = [
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
        },
        resolve : {
            groupId : ['GroupService','$stateParams','$state', function(GroupService, $stateParams, $state){
                if(!$stateParams.groupId){
                    var courseId = $stateParams.courseId;
                    var moduleId = $stateParams.moduleId;
                    GroupService.initialize(courseId, moduleId)
                        .then(function(payload){
                            $state.go($state.current, {groupId:payload.id});
                        return payload.id;
                    }, function(err){
                            $state.go('app.course.assignment', {moduleId : moduleId});
                        return err;
                    });
                }else{
                    return $stateParams.groupId;
                }
            }]
        }
    },
    {
        name :  'app.course.assignment.login',
        url : '/group/{groupId}/login',
        views : {
            'assignmentContent' : {
                templateUrl : 'views/student/assignment_group_login/home.html',
                controller : 'Student.AssignmentLoginCtrl',
                controllerAs : 'assignmentLogin'
            }
        }
    },
    {
        name : 'app.course.assignment.questions',
        url : '/group/{groupId}/questions',
        views : {
            'assignmentContent' : {
                templateUrl : 'views/student/assignment_questions/home.html',
                controller : 'Student.AssignmentQuestionsCtrl',
                controllerAs : 'assignmentQuestions'
            }
        },
        resolve : {
            lock : ['GroupService','$stateParams','$state', function(GroupService, $stateParams, $state){
                var courseId = $stateParams.courseId;
                var moduleId = $stateParams.moduleId;
                var groupId = $stateParams.groupId;
                GroupService.getLock(courseId, moduleId, groupId)
                    .then(function(payload){
                        if(!payload){
                            $state.go('app.course.assignment', {moduleId : moduleId});
                        }
                    return payload;
                }, function(err){
                        $state.go('app.course.assignment', {moduleId : moduleId});
                    return err;
                });
            }]
        }
    }
]

module.exports = states;
