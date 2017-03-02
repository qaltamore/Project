(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerDialogController', QuestionAnswerDialogController);

    QuestionAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionAnswer'];

    function QuestionAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionAnswer) {
        var vm = this;

        vm.questionAnswer = entity;
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
            if (vm.questionAnswer.id !== null) {
                QuestionAnswer.update(vm.questionAnswer, onSaveSuccess, onSaveError);
            } else {
                QuestionAnswer.save(vm.questionAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hack47App:questionAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
