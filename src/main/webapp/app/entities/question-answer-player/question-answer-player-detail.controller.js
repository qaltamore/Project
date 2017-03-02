(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('QuestionAnswerPlayerDetailController', QuestionAnswerPlayerDetailController);

    QuestionAnswerPlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionAnswerPlayer', 'Player', 'QuestionAnswer'];

    function QuestionAnswerPlayerDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionAnswerPlayer, Player, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerPlayer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hack47App:questionAnswerPlayerUpdate', function(event, result) {
            vm.questionAnswerPlayer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
