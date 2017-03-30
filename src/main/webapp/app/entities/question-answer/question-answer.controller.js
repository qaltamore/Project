(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerController', QuestionAnswerController);

    QuestionAnswerController.$inject = ['$scope', '$state', 'QuestionAnswer'];

    function QuestionAnswerController ($scope, $state, QuestionAnswer) {
        var vm = this;

        vm.questionAnswers = [];

        loadAll();

        function loadAll() {
            QuestionAnswer.query(function(result) {
                vm.questionAnswers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
