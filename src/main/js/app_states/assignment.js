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
            groupId : ['GroupService','$stateParams','$state', '$q', function(GroupService, $stateParams, $state, $q){
                var deferred = $q.defer();
                if(!$stateParams.groupId){
                    var courseId = $stateParams.courseId;
                    var moduleId = $stateParams.moduleId;
                    GroupService.initialize(courseId, moduleId)
                        .then(function(payload){
                            $stateParams.groupId = payload.id;
                            deferred.resolve(payload.id);
                    }, function(err){
                        deferred.reject("ERROR Initializing the group");
                    });
                } else {
                    deferred.resolve($stateParams.groupId);
                }

                return deferred.promise;
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
                return GroupService.getLock(courseId, moduleId, groupId);
            }]
        }
    },
    {
        name : 'app.course.assignments_add_edit',
        url : '/{moduleId}/add_edit',
        views : {
            'childContent' : {
                templateUrl : 'views/instructor/assignments_add_edit/home.html',
                controller : 'Instructor.AssignmentsAddEdit',
                controllerAs : 'assignmentsEditCtrl'
            }
        },
        params  : {
            isNew : false
        },
        resolve : {
            assignment : ['AssignmentService','$q','$stateParams', '$state', function(AssignmentService, $q, $stateParams, $state){
                var deferred = $q.defer();
                if($stateParams.moduleId == "new"){
                    deferred.resolve(null);
                } else {
                    AssignmentService.getAssignmentDetails($stateParams.courseId, $stateParams.moduleId)
                        .then(function(payload){
                        deferred.resolve(payload);
                    }, function(err){
                        deferred.reject("ERROR getting Assignmet Details");
                    });
                }
                return deferred.promise;
            }]
        }
    },
    {
        name : 'app.course.assignments_add_edit_pages',
        url : '/{moduleId}/add_edit_pages',
        views : {
            'childContent' : {
                templateUrl : 'views/instructor/assignments_add_edit_pages/home.html',
                controller : 'Instructor.PagesAddEdit',
                controllerAs : 'pagesEditCtrl'
            }
        }
    },
    {
        name : 'app.course.assignments_add_edit_questions',
        url : '/{moduleId}/add_edit_pages/{pageNum}/add_edit_questions',
        views : {
            'childContent' : {
                templateUrl : 'views/instructor/assignments_add_edit_questions/home.html',
                controller : 'Instructor.QuestionsAddEdit',
                controllerAs : 'questionsEditCtrl'
            }
        }
    },
    {
        name : 'app.course.assignments_add_edit_question',
        url : '/{moduleId}/add_edit_pages/{pageNum}/add_edit_questions/{questionId}',
        params : {
            isNew : false,
            questionType : null
        },
        views : {
            'childContent' : {
                templateUrl : 'views/instructor/assignments_add_edit_question/home.html',
                controller : 'Instructor.QuestionAddEdit',
                controllerAs : 'questionEditCtrl'
            }
        }
    },
]

module.exports = states;
