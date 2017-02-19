
function Controller($state, $uibModal, $uibModalInstance){
    "ngInject";
    this._$uibModalInstance = $uibModalInstance;
    this._$state = $state;
}

Controller.prototype.goBack = function(){
    var self = this;
    console.log("I cicked ok");
    self._$uibModalInstance.close();
};

Controller.prototype.reload = function(){
    var self = this;
    console.log("I clicked Reload");
}

module.exports = Controller;
