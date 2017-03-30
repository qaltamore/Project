(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerPlayerController', QuestionAnswerPlayerController);

    QuestionAnswerPlayerController.$inject = ['$scope', '$state', 'QuestionAnswerPlayer'];

    function QuestionAnswerPlayerController ($scope, $state, QuestionAnswerPlayer) {
        var vm = this;

        vm.questionAnswerPlayers = [];

        loadAll();

        function loadAll() {
            QuestionAnswerPlayer.query(function(result) {
                vm.questionAnswerPlayers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
