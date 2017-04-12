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
        url: '/{email}/add_edit',
        params: {
            email,
            firstName,
            lastName,
            appRoles
        },
        views: {
            'mainContent@app': {
                templateUrl: 'views/admin/user_add_edit/home.html',
                controller: 'AdminAddEditCtrl',
                controllerAs: 'adminAddEditCtrl'
            }
<<<<<<< HEAD
=======
        },
        resolve : {
            user : ['AdminService', 'SessionService', '$stateParams', '$state', function (AdminService, SessionService, $stateParams, $state) {
                if ($stateParams.userEmail == "new") {
                    return null
                } else {
                    console.log($stateParams.userEmail);
                    return AdminService.getUser($stateParams.userEmail)
                        .then(function (user) {
                            return user;
                        }, function (err) {
                            $state.go('app.admin', null, { reload: true, location: 'replace' });
                            return err;
                        });
                }
            }]
>>>>>>> parent of e21494a... Redirect after adding user
        }
    }
]
module.exports = states;
