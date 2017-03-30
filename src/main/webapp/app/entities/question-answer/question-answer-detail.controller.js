(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerDetailController', QuestionAnswerDetailController);

    QuestionAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionAnswer'];

    function QuestionAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionAnswer) {
        var vm = this;

        vm.questionAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:questionAnswerUpdate', function(event, result) {
            vm.questionAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
