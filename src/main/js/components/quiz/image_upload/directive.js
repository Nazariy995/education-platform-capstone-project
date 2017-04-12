function Directive($state, QuestionService){
    "ngInject";

    function link(scope, element, attributes){
        //set up initial variables
        scope.uploaded = false;
        scope.error = false;

        scope.model = {
            type : scope.field.questionType
        };

        setAnswer(scope);
        var _scope = scope;
        //Upload image upon the click of the Upload Button
        scope.uploadImage = function(file){
            //reset the check variables
            scope.uploaded = false;
            scope.error = false;
            if(file){
                fileUpload(file, _scope);
            } else {
                _scope.error = true;
            }
        }

    }

    //Purpose: Upload Image on the click of the Upload button
    //Params: file - Upload Image, Scope - Object
    function fileUpload( file, scope ) {
        var _scope = scope;
        QuestionService.uploadImage(file).then(function(res){
            _scope.uploaded = true;
            //set the model answer
            _scope.model = {
                type : _scope.field.questionType
            };
            _scope.model.answer = res;
        }, function(err){
            _scope.error = true;
            console.log(err);
        })

    }

    //Purpose: Set the answer on load if answer is available
    //Params: scope - Object
    function setAnswer( scope ){
        var _scope = scope;
        scope.$watch('savedAnswers', function(newAnswer){
            if(newAnswer && "value" in newAnswer){
                _scope.model = {
                    type : _scope.field.questionType
                };
                _scope.model.answer = newAnswer.value.answer;
            }
        });
    }

    var directive = {
        templateUrl: 'components/quiz/image_upload/home.html',
        link : link,
        scope: {
            model: '=',
            savedAnswers : '=',
            editable : '=',
            field: '='
        }
    }

    return directive;
}

module.exports =  Directive;
