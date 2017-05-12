(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('GameController', GameController);

    GameController.$inject = ['$scope', 'Principal', 'GameService', 'Personnage', '$state'];

    function GameController ($scope, Principal, GameService, Personnage, $state) {
        var vm = this;
        
        vm.game = GameService.game;
        
        vm.personnages = [];
        //vm
        
        loadAllPersonnages();
        
        function loadAllPersonnages() {
        	Personnage.query(function(result) {
        		if(vm.game.role == "atk") {
            		var sliced = result.slice(0, 4);
            		vm.personnages = sliced;
            		GameService.game.role = "atk";
            		GameService.game.roleTxt = "Attaquant";
            	} else {
            		var sliced = result.slice(4);
            		vm.personnages = sliced;
            		GameService.game.role = "def";
            		GameService.game.roleTxt = "Défenseur";
            	}
        		vm.searchQuery = null;
        	});
        }
    }
})();
