function Directive($state, QuestionService){
    "ngInject";

    function link(scope, element, attributes){

        scope.model = {
            type : scope.field.questionType
        };

//        setAnswer(scope);
        scope.uploadImage = function(file){
            fileUpload(file, scope);
        }

    }

    function fileUpload( file, scope ) {
        QuestionService.uploadImage(file).then(function(res){
            console.log("done Uploading");

        }, function(err){
            console.log(err);
        })

    }

    function setAnswer( scope ){
        var _scope = scope;
        scope.$watch('savedAnswers', function(newAnswer){
            if(newAnswer && "value" in newAnswer){
                _scope.model.answer = newAnswer.value.answer;
            }
        });
    }

    var directive = {
        controller: 'InputComponentController',
        controllerAs: 'componentCtrl',
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
