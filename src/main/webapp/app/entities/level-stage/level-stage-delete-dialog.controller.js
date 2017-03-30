(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('LevelStageDeleteController',LevelStageDeleteController);

    LevelStageDeleteController.$inject = ['$uibModalInstance', 'entity', 'LevelStage'];

    function LevelStageDeleteController($uibModalInstance, entity, LevelStage) {
        var vm = this;

        vm.levelStage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LevelStage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
