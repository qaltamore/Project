(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnageController', PersonnageController);

    PersonnageController.$inject = ['$scope', '$state', 'DataUtils', 'Personnage'];

    function PersonnageController ($scope, $state, DataUtils, Personnage) {
        var vm = this;

        vm.personnages = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Personnage.query(function(result) {
                vm.personnages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
