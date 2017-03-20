
function Service($uibModal, $document){
    "ngInject";

    this._$uibModal = $uibModal;
    this._$document = $document;
}

Service.prototype.open = function(size, confirmationText, footnoteText){
    var self = this;
    var modalInstance = self._$uibModal.open({
        animation: true,
        templateUrl: 'components/confirmation_modal/template.html',
        controller:  'ConfirmationModalController',
        controllerAs: '$modalCtrl',
        size: size,
        resolve : {
            confirmationText : function(){
                return confirmationText;
            },
            footnoteText : function() {
                return footnoteText;
            }
        }
    });

    return modalInstance;
}

module.exports = Service;
