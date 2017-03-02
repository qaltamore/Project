(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('BuildingTypeDeleteController',BuildingTypeDeleteController);

    BuildingTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'BuildingType'];

    function BuildingTypeDeleteController($uibModalInstance, entity, BuildingType) {
        var vm = this;

        vm.buildingType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BuildingType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
