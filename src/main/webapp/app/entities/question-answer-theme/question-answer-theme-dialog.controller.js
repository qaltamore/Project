(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerThemeDialogController', QuestionAnswerThemeDialogController);

    QuestionAnswerThemeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionAnswerTheme', 'Theme', 'QuestionAnswer'];

    function QuestionAnswerThemeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionAnswerTheme, Theme, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerTheme = entity;
        vm.clear = clear;
        vm.save = save;
        vm.themes = Theme.query();
        vm.questionanswers = QuestionAnswer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.questionAnswerTheme.id !== null) {
                QuestionAnswerTheme.update(vm.questionAnswerTheme, onSaveSuccess, onSaveError);
            } else {
                QuestionAnswerTheme.save(vm.questionAnswerTheme, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hack47App:questionAnswerThemeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
