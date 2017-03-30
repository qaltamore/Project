(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnageDeleteController',PersonnageDeleteController);

    PersonnageDeleteController.$inject = ['$uibModalInstance', 'entity', 'Personnage'];

    function PersonnageDeleteController($uibModalInstance, entity, Personnage) {
        var vm = this;

        vm.personnage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Personnage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
