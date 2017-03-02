(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnageDialogController', PersonnageDialogController);

    PersonnageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Personnage'];

    function PersonnageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Personnage) {
        var vm = this;

        vm.personnage = entity;
        vm.clear = clear;
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
            $scope.$emit('hack47App:personnageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
