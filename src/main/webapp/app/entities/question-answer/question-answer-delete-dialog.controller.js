(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerDeleteController',QuestionAnswerDeleteController);

    QuestionAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionAnswer'];

    function QuestionAnswerDeleteController($uibModalInstance, entity, QuestionAnswer) {
        var vm = this;

        vm.questionAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
