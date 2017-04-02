
function Service($uibModal, $document){
    "ngInject";

    this._$uibModal = $uibModal;
    this._$document = $document;
}

Service.prototype.open = function(size){
    var self = this;
    var modalInstance = self._$uibModal.open({
        animation: true,
        templateUrl: 'components/editor_image_upload_modal/template.html',
        controller:  'EditorUploadModalController',
        controllerAs: '$modalCtrl',
        size: size
    });

    return modalInstance;
}

module.exports = Service;
