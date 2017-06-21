(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('Personnage2DialogController', Personnage2DialogController);

    Personnage2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Personnage', 'GameService'];

    function Personnage2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Personnage, GameService) {
        var vm = this;

        vm.personnage = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var pvPerso = vm.personnage.name + "PV";
            var manaPerso = vm.personnage.name + "MANA";
            if(vm.personnage.lifePoints <= GameService.listePersonnages[pvPerso] && vm.personnage.magicPoints <= GameService.listePersonnages[manaPerso]) {
            	//console.log("PV saisis : " + vm.personnage.lifePoints + " & PV de la liste : " + GameService.listePersonnages[vm.personnage.name]);
            	if (vm.personnage.id !== null) {
	                Personnage.update(vm.personnage, onSaveSuccess, onSaveError);
	            } else {
	                Personnage.save(vm.personnage, onSaveSuccess, onSaveError);
	            }
            } else {
            	alert("Ce personnage ne peut avoir plus de " + GameService.listePersonnages[pvPerso] + " points de vie et " +  GameService.listePersonnages[manaPerso] + " points de magie");
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jHipsterAppliApp:personnageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
