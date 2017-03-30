(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('BuildingTypeLevelStageDeleteController',BuildingTypeLevelStageDeleteController);

    BuildingTypeLevelStageDeleteController.$inject = ['$uibModalInstance', 'entity', 'BuildingTypeLevelStage'];

    function BuildingTypeLevelStageDeleteController($uibModalInstance, entity, BuildingTypeLevelStage) {
        var vm = this;

        vm.buildingTypeLevelStage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BuildingTypeLevelStage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
