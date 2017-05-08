
/**
 * Request/Respoce interceptor
 *
 * @param {Object} $injector - From Angular
 * @param {Object} $q - Used for sending a rejection upon a 401 in reponseError
 * @param {Object} SessionService  - used for setting the AuthToken
 * @export
 */
function Interceptor($injector, $q, SessionService){
    "ngInject";

    //Purpose: Catch request, response, and responseError to respond
    var sessionInjector = {
        //Purpose:  Catch the request
        //Params: config - {} - From Angular
        request: function(config) {
            if(SessionService.getAccessToken() != null ) {
                //set the auth token for session
                config.headers['X-Auth-Token'] = SessionService.getAccessToken();
            }

            return config;
        },
        //Purpose: Catch the response
        //Params: response - {} - From Angular
        response: function(response) {
            var authToken = response.headers("x-auth-token");
            if( authToken != null && authToken != "") {
                //set a new access token if it is availbale from the request
                SessionService.setAccessToken(authToken);
            }

            return response;
        },
        //Purpose: Catch the responseError
        //Params: response - {} - From Angular
        responseError : function(response) {
            //Check for session timeout
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


