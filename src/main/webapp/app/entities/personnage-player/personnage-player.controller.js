(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnagePlayerController', PersonnagePlayerController);

    PersonnagePlayerController.$inject = ['$scope', '$state', 'PersonnagePlayer'];

    function PersonnagePlayerController ($scope, $state, PersonnagePlayer) {
        var vm = this;

        vm.personnagePlayers = [];

        loadAll();

        function loadAll() {
            PersonnagePlayer.query(function(result) {
                vm.personnagePlayers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
