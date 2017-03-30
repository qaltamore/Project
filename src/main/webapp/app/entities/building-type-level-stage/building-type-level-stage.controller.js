(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('BuildingTypeLevelStageController', BuildingTypeLevelStageController);

    BuildingTypeLevelStageController.$inject = ['$scope', '$state', 'BuildingTypeLevelStage'];

    function BuildingTypeLevelStageController ($scope, $state, BuildingTypeLevelStage) {
        var vm = this;

        vm.buildingTypeLevelStages = [];

        loadAll();

        function loadAll() {
            BuildingTypeLevelStage.query(function(result) {
                vm.buildingTypeLevelStages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
