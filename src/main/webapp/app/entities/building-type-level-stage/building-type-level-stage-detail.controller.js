(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('BuildingTypeLevelStageDetailController', BuildingTypeLevelStageDetailController);

    BuildingTypeLevelStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BuildingTypeLevelStage', 'LevelStage', 'BuildingType'];

    function BuildingTypeLevelStageDetailController($scope, $rootScope, $stateParams, previousState, entity, BuildingTypeLevelStage, LevelStage, BuildingType) {
        var vm = this;

        vm.buildingTypeLevelStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:buildingTypeLevelStageUpdate', function(event, result) {
            vm.buildingTypeLevelStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
