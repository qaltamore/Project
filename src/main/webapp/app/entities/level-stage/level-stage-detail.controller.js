(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('LevelStageDetailController', LevelStageDetailController);

    LevelStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LevelStage'];

    function LevelStageDetailController($scope, $rootScope, $stateParams, previousState, entity, LevelStage) {
        var vm = this;

        vm.levelStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hack47App:levelStageUpdate', function(event, result) {
            vm.levelStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
