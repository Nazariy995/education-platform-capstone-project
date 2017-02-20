
function Service($uibModal, $document){
    "ngInject";

    this._$uibModal = $uibModal;
    this._$document = $document;
}

Service.prototype.open = function(size){
    var self = this;
    var modalInstance = self._$uibModal.open({
        animation: true,
        templateUrl: 'components/popup_modal/template.html',
        controller:  'PopupModalController',
        controllerAs: '$modalCtrl',
        size: size
    });
}

module.exports = Service;
