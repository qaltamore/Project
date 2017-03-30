(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('LevelStageDialogController', LevelStageDialogController);

    LevelStageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LevelStage'];

    function LevelStageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LevelStage) {
        var vm = this;

        vm.levelStage = entity;
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
            if (vm.levelStage.id !== null) {
                LevelStage.update(vm.levelStage, onSaveSuccess, onSaveError);
            } else {
                LevelStage.save(vm.levelStage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jHipsterAppliApp:levelStageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
