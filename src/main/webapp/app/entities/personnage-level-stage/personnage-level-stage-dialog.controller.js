(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnageLevelStageDialogController', PersonnageLevelStageDialogController);

    PersonnageLevelStageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonnageLevelStage', 'Personnage', 'LevelStage'];

    function PersonnageLevelStageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonnageLevelStage, Personnage, LevelStage) {
        var vm = this;

        vm.personnageLevelStage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.personnages = Personnage.query();
        vm.levelstages = LevelStage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personnageLevelStage.id !== null) {
                PersonnageLevelStage.update(vm.personnageLevelStage, onSaveSuccess, onSaveError);
            } else {
                PersonnageLevelStage.save(vm.personnageLevelStage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hack47App:personnageLevelStageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
