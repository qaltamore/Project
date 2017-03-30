(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('QuestionAnswerPlayerDetailController', QuestionAnswerPlayerDetailController);

    QuestionAnswerPlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionAnswerPlayer', 'Player', 'QuestionAnswer'];

    function QuestionAnswerPlayerDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionAnswerPlayer, Player, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerPlayer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:questionAnswerPlayerUpdate', function(event, result) {
            vm.questionAnswerPlayer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
