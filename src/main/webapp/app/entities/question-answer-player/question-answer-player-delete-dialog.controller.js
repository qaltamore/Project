(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerPlayerDeleteController',QuestionAnswerPlayerDeleteController);

    QuestionAnswerPlayerDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionAnswerPlayer'];

    function QuestionAnswerPlayerDeleteController($uibModalInstance, entity, QuestionAnswerPlayer) {
        var vm = this;

        vm.questionAnswerPlayer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionAnswerPlayer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
