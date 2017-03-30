(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('LevelStageDetailController', LevelStageDetailController);

    LevelStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LevelStage'];

    function LevelStageDetailController($scope, $rootScope, $stateParams, previousState, entity, LevelStage) {
        var vm = this;

        vm.levelStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:levelStageUpdate', function(event, result) {
            vm.levelStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
