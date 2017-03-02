(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('PersonnagePlayerDialogController', PersonnagePlayerDialogController);

    PersonnagePlayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonnagePlayer', 'Player', 'Personnage'];

    function PersonnagePlayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonnagePlayer, Player, Personnage) {
        var vm = this;

        vm.personnagePlayer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.players = Player.query();
        vm.personnages = Personnage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personnagePlayer.id !== null) {
                PersonnagePlayer.update(vm.personnagePlayer, onSaveSuccess, onSaveError);
            } else {
                PersonnagePlayer.save(vm.personnagePlayer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hack47App:personnagePlayerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
