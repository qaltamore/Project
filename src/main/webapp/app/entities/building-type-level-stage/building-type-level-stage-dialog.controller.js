(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('BuildingTypeLevelStageDialogController', BuildingTypeLevelStageDialogController);

    BuildingTypeLevelStageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BuildingTypeLevelStage', 'LevelStage', 'BuildingType'];

    function BuildingTypeLevelStageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BuildingTypeLevelStage, LevelStage, BuildingType) {
        var vm = this;

        vm.buildingTypeLevelStage = entity;
        vm.clear = clear;
        vm.save = save;
        vm.levelstages = LevelStage.query();
        vm.buildingtypes = BuildingType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.buildingTypeLevelStage.id !== null) {
                BuildingTypeLevelStage.update(vm.buildingTypeLevelStage, onSaveSuccess, onSaveError);
            } else {
                BuildingTypeLevelStage.save(vm.buildingTypeLevelStage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jHipsterAppliApp:buildingTypeLevelStageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
