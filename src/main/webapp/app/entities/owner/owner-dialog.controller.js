(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('OwnerDialogController', OwnerDialogController);

    OwnerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Owner'];

    function OwnerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Owner) {
        var vm = this;

        vm.owner = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.owner.id !== null) {
                Owner.update(vm.owner, onSaveSuccess, onSaveError);
            } else {
                Owner.save(vm.owner, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jHipsterAppliApp:ownerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImg = function ($file, owner) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        owner.img = base64Data;
                        owner.imgContentType = $file.type;
                    });
                });
            }
        };

    }
})();
