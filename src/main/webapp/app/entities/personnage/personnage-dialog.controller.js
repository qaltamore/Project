(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnageDialogController', PersonnageDialogController);

    PersonnageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Personnage'];

    function PersonnageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Personnage) {
        var vm = this;

        vm.personnage = entity;
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
            if (vm.personnage.id !== null) {
                Personnage.update(vm.personnage, onSaveSuccess, onSaveError);
            } else {
                Personnage.save(vm.personnage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jHipsterAppliApp:personnageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImg = function ($file, personnage) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        personnage.img = base64Data;
                        personnage.imgContentType = $file.type;
                    });
                });
            }
        };

    }
})();
