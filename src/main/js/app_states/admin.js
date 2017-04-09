var states = [
    {
        name: 'app.admin',
        url: 'admin',
        params: {
            created_updated: false
        },
        views: {
            'mainContent@app': {
                templateUrl: 'views/admin/home/home.html',
                controller: 'AdminCtrl',
                controllerAs: 'adminCtrl'
            }
        }
    },
    {
        name: 'app.admin.add_edit',
        url: '/{userID}/add_edit',
        views: {
            'mainContent@app': {
                templateUrl: 'views/admin/user_add_edit',
                controller: 'AdminAddEditCtrl',
                controllerAs: 'adminAddEditCtrl'
            }
        },
        resolve: {
            course: ['AdminServer', 'SessionService', '$stateParams', '$state', function (AdminService, SessionService, $stateParams, $state) {
                if ($stateParams.courseId == "new") {
                    return null
                } else {
                    return AdminService.getUser($stateParams.courseId)
                        .then(function (course) {
                            return course;
                        }, function (err) {
                            $state.go('app.courses', null, { reload: true, location: 'replace' });
                            return err;
                        });
                }
            }]
        }
    }
]
module.exports = states;
