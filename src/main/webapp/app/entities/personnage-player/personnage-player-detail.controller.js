(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnagePlayerDetailController', PersonnagePlayerDetailController);

    PersonnagePlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonnagePlayer', 'Player', 'Personnage'];

    function PersonnagePlayerDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonnagePlayer, Player, Personnage) {
        var vm = this;

        vm.personnagePlayer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hack47App:personnagePlayerUpdate', function(event, result) {
            vm.personnagePlayer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
