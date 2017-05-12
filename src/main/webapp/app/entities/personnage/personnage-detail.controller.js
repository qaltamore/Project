(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnageDetailController', PersonnageDetailController);

    PersonnageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Personnage'];

    function PersonnageDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Personnage) {
        var vm = this;

        vm.personnage = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:personnageUpdate', function(event, result) {
            vm.personnage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
