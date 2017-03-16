
function Controller($state, $uibModalInstance, confirmationText, footnoteText){
    "ngInject";
    this._$uibModalInstance = $uibModalInstance;
    this._$state = $state;
    this.text = confirmationText;
    this.footnoteText = footnoteText;
}

Controller.prototype.confirm = function(){
    var self = this;
    console.log("I cicked Yes");
    self._$uibModalInstance.close();
};

Controller.prototype.reject = function(){
    var self = this;
    console.log("I clicked No");
    self._$uibModalInstance.dismiss('cancel');

}

module.exports = Controller;
