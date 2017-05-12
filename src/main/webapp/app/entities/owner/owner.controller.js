(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('OwnerController', OwnerController);

    OwnerController.$inject = ['$scope', '$state', 'DataUtils', 'Owner'];

    function OwnerController ($scope, $state, DataUtils, Owner) {
        var vm = this;

        vm.owners = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Owner.query(function(result) {
                vm.owners = result;
                vm.searchQuery = null;
            });
        }
    }
})();
