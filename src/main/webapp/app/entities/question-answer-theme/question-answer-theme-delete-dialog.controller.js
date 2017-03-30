(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerThemeDeleteController',QuestionAnswerThemeDeleteController);

    QuestionAnswerThemeDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionAnswerTheme'];

    function QuestionAnswerThemeDeleteController($uibModalInstance, entity, QuestionAnswerTheme) {
        var vm = this;

        vm.questionAnswerTheme = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionAnswerTheme.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
