(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('BuildingTypeDialogController', BuildingTypeDialogController);

    BuildingTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BuildingType'];

    function BuildingTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BuildingType) {
        var vm = this;

        vm.buildingType = entity;
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
            if (vm.buildingType.id !== null) {
                BuildingType.update(vm.buildingType, onSaveSuccess, onSaveError);
            } else {
                BuildingType.save(vm.buildingType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hack47App:buildingTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
