function Provider($provide){
    "ngInject";

    $provide.decorator('taOptions', ['taRegisterTool', 'taSelection', 'taToolFunctions', 'EditorUploadService' ,'$delegate', function(taRegisterTool, taSelection, taToolFunctions, EditorUploadService, taOptions) { // $delegate is the taOptions we are decorating
        taRegisterTool('uploadInsertImage', {
            iconclass : 'fa fa-cloud-upload',
            tooltiptext : 'Upload And Insert Image',
            action: function($deferred, restoreSelection) {
                var self = this;
                var modalInstance = EditorUploadService.open("");
                modalInstance.result.then(function(imageURL){
                    if (taSelection.getSelectionElement().tagName && taSelection.getSelectionElement().tagName.toLowerCase() === 'a') {
                        // due to differences in implementation between FireFox and Chrome, we must move the
                        // insertion point past the <a> element, otherwise FireFox inserts inside the <a>
                        // With this change, both FireFox and Chrome behave the same way!
                        taSelection.setSelectionAfterElement(taSelection.getSelectionElement());
                    }
                    var embed = '<img src="' + imageURL + '" style="width:50%">';
                    restoreSelection();
                    self.$editor().wrapSelection('insertHTML', embed, true);
                    $deferred.resolve();
                }, function(){
                    restoreSelection();
                    $deferred.resolve();
                });
                return false;
            },
            onElementSelect: {
                element: 'img',
                action: taToolFunctions.imgOnSelectAction
            }
        });
        taOptions.toolbar[3].unshift('uploadInsertImage');
        return taOptions;
    }]);
};

module.exports = Provider;
