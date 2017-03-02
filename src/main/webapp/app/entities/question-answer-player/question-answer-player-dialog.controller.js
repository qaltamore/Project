(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerPlayerDialogController', QuestionAnswerPlayerDialogController);

    QuestionAnswerPlayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionAnswerPlayer', 'Player', 'QuestionAnswer'];

    function QuestionAnswerPlayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionAnswerPlayer, Player, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerPlayer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.players = Player.query();
        vm.questionanswers = QuestionAnswer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.questionAnswerPlayer.id !== null) {
                QuestionAnswerPlayer.update(vm.questionAnswerPlayer, onSaveSuccess, onSaveError);
            } else {
                QuestionAnswerPlayer.save(vm.questionAnswerPlayer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hack47App:questionAnswerPlayerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
