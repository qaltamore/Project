(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnageDetailController', PersonnageDetailController);

    PersonnageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Personnage'];

    function PersonnageDetailController($scope, $rootScope, $stateParams, previousState, entity, Personnage) {
        var vm = this;

        vm.personnage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:personnageUpdate', function(event, result) {
            vm.personnage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
