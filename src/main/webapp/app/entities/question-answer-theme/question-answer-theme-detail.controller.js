(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerThemeDetailController', QuestionAnswerThemeDetailController);

    QuestionAnswerThemeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionAnswerTheme', 'Theme', 'QuestionAnswer'];

    function QuestionAnswerThemeDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionAnswerTheme, Theme, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerTheme = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hack47App:questionAnswerThemeUpdate', function(event, result) {
            vm.questionAnswerTheme = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
