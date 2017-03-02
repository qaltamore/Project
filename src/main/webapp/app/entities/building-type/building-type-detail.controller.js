(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('BuildingTypeDetailController', BuildingTypeDetailController);

    BuildingTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BuildingType'];

    function BuildingTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, BuildingType) {
        var vm = this;

        vm.buildingType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hack47App:buildingTypeUpdate', function(event, result) {
            vm.buildingType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
