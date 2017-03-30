(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnagePlayerDeleteController',PersonnagePlayerDeleteController);

    PersonnagePlayerDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonnagePlayer'];

    function PersonnagePlayerDeleteController($uibModalInstance, entity, PersonnagePlayer) {
        var vm = this;

        vm.personnagePlayer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonnagePlayer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
