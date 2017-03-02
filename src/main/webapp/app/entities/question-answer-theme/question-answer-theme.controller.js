(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerThemeController', QuestionAnswerThemeController);

    QuestionAnswerThemeController.$inject = ['$scope', '$state', 'QuestionAnswerTheme'];

    function QuestionAnswerThemeController ($scope, $state, QuestionAnswerTheme) {
        var vm = this;

        vm.questionAnswerThemes = [];

        loadAll();

        function loadAll() {
            QuestionAnswerTheme.query(function(result) {
                vm.questionAnswerThemes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
