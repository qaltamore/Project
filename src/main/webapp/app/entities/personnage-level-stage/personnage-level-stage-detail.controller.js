(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnageLevelStageDetailController', PersonnageLevelStageDetailController);

    PersonnageLevelStageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonnageLevelStage', 'Personnage', 'LevelStage'];

    function PersonnageLevelStageDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonnageLevelStage, Personnage, LevelStage) {
        var vm = this;

        vm.personnageLevelStage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hack47App:personnageLevelStageUpdate', function(event, result) {
            vm.personnageLevelStage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
