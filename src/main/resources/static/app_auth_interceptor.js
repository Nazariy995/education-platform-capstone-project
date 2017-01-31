
function Interceptor(SessionService){
    "ngInject";

    var sessionInjector = {
        request: function(config) {
            if(SessionService.getAccessToken() != null) {
//                console.log(SessionService.getAccessToken());
                config.headers['X-Auth-Token'] = SessionService.getAccessToken();
//                console.log("I set the auth token")
            }
//            console.log("Im inside session Injector");
//            console.log(config);

            return config;
        },
        response: function(response) {
            var authToken = response.headers("x-auth-token");
            if( authToken != null ) {
                SessionService.setAccessToken(authToken);
                console.log("New Auth Token");
                console.log(authToken);
            }

            return response;
        }
    };

    return sessionInjector;
}

module.exports = Interceptor;


