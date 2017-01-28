
function Interceptor(SessionService){
    "ngInject";

    var sessionInjector = {
        request: function(config) {
            if(SessionService.getAccessToken() != null) {
                console.log(SessionService.getAccessToken());
                config.headers['X-Auth-Token'] = SessionService.getAccessToken();
                console.log("I set the auth token")
            }
            console.log("Im inside session Injector");
//            console.log(config);

            return config;
        }
    };

    return sessionInjector;
}

module.exports = Interceptor;


