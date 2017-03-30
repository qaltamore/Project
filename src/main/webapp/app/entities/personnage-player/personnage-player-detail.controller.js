(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnagePlayerDetailController', PersonnagePlayerDetailController);

    PersonnagePlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonnagePlayer', 'Player', 'Personnage'];

    function PersonnagePlayerDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonnagePlayer, Player, Personnage) {
        var vm = this;

        vm.personnagePlayer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:personnagePlayerUpdate', function(event, result) {
            vm.personnagePlayer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
