
function Interceptor($injector, $q, SessionService){
    "ngInject";

    var sessionInjector = {
        request: function(config) {
            if(SessionService.getAccessToken() != null ) {
                //set the auth token for session
                config.headers['X-Auth-Token'] = SessionService.getAccessToken();
            }

            return config;
        },
        response: function(response) {
            var authToken = response.headers("x-auth-token");
            if( authToken != null && authToken != "") {
                //set a new access token if it is availbale from the request
                SessionService.setAccessToken(authToken);
            }

            return response;
        },
        responseError : function(response) {
            if(response.status == 401){
                SessionService.destroy();
                $injector.get('$state').transitionTo('app.login', { sessionExpired : true }, {reload : true})   ;
                return;
            }
            return $q.reject(response);
        }
    };

    return sessionInjector;
}

module.exports = Interceptor;


