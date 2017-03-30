(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('TeamController', TeamController);

    TeamController.$inject = ['$scope', '$state', 'Personnage'];

    function TeamController ($scope, $state, Personnage) {
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
