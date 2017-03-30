(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerThemeDetailController', QuestionAnswerThemeDetailController);

    QuestionAnswerThemeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionAnswerTheme', 'Theme', 'QuestionAnswer'];

    function QuestionAnswerThemeDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionAnswerTheme, Theme, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerTheme = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:questionAnswerThemeUpdate', function(event, result) {
            vm.questionAnswerTheme = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
