(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnageLevelStageDeleteController',PersonnageLevelStageDeleteController);

    PersonnageLevelStageDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonnageLevelStage'];

    function PersonnageLevelStageDeleteController($uibModalInstance, entity, PersonnageLevelStage) {
        var vm = this;

        vm.personnageLevelStage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonnageLevelStage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
