(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnageController', PersonnageController);

    PersonnageController.$inject = ['$scope', '$state', 'Personnage'];

    function PersonnageController ($scope, $state, Personnage) {
        var vm = this;

        vm.personnages = [];

        loadAll();

        function loadAll() {
            Personnage.query(function(result) {
                vm.personnages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
