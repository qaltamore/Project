(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('BuildingTypeController', BuildingTypeController);

    BuildingTypeController.$inject = ['$scope', '$state', 'BuildingType'];

    function BuildingTypeController ($scope, $state, BuildingType) {
        var vm = this;

        vm.buildingTypes = [];

        loadAll();

        function loadAll() {
            BuildingType.query(function(result) {
                vm.buildingTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
