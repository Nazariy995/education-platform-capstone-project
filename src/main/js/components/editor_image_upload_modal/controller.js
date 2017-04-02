
function Controller($state, $uibModalInstance, QuestionService){
    "ngInject";
    this._$uibModalInstance = $uibModalInstance;
    this._QuestionService = QuestionService;
    this._$state = $state;
    this.error = false;
}

Controller.prototype.confirm = function(imageURL){
    var self = this;
    self._$uibModalInstance.close(imageURL);
};

Controller.prototype.reject = function(){
    var self = this;
    self._$uibModalInstance.dismiss('cancel');

}

Controller.prototype.uploadImage = function(file){
    var self = this;
    self._QuestionService.uploadImage(file)
        .then(function(res){
        self.confirm(res);
    }, function(err){
        self.error = "ERROR uploading the file!";
    });
};

module.exports = Controller;
