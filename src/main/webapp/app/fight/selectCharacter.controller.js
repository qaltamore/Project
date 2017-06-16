(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('SelectCharacterController', SelectCharacterController);

    SelectCharacterController.$inject = ['$scope', '$state', 'Player', 'Personnage', 'GameService', 'FightService'];

    function SelectCharacterController ($scope, $state, Player, Personnage, GameService, FightService) {
        var vm = this;

        vm.game = GameService.game;
        vm.players = [];
        vm.personnages = [];
        vm.personnagesAttaquant = [];
        vm.personnagesDefenseur = [];
        
        vm.atkSelected = null;
        vm.defSelected = null;
        
        vm.selectCharacter = selectCharacter;
        
        vm.test = test;
        
        loadAllCharacters();
        
        function loadAllCharacters() {
        	Personnage.query(function(result) {
        		var attaquants = result.slice(0, 4);
                var defenseurs = result.slice(4);

            	vm.personnages = result;
                vm.personnagesAttaquant = attaquants;
                vm.personnagesDefenseur = defenseurs;
        		vm.searchQuery = null;
        	});
        }
        
        function selectCharacter(id, role, character) {

        	if((role == "atk" && vm.atkSelected == null) || (role == "def" && vm.defSelected == null)) {
	        	var img = document.getElementById(id);
	        	img.style.border = "1px red solid";
	        	if(role == "atk")
	        		vm.atkSelected = character;
	        	else
	        		vm.defSelected = character;
        	}
        }
        
        function test() {
        	FightService.atk = vm.atkSelected;
        	FightService.def = vm.defSelected;
        	if(vm.atkSelected != 0 && vm.defSelected != 0) {
        		$state.go('fight');
        	}
        }
    }
})();
