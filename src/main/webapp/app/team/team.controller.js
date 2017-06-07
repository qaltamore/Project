(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('TeamController', TeamController);

    TeamController.$inject = ['$scope', '$state', 'Personnage', 'GameService'];

    function TeamController ($scope, $state, Personnage, GameService) {
        var vm = this;

        vm.game = GameService.game;
        vm.personnages = [];

        loadAll();

        function loadAll() {
            Personnage.query(function(result) {
            	
            	if(vm.game.role == "ATK") {
            		var sliced = result.slice(0, 4);
            		vm.personnages = sliced;
            		GameService.game.role = "ATK";
            		GameService.game.roleTxt = "Attaquant";
            	} else {
            		var sliced = result.slice(4);
            		vm.personnages = sliced;
            		GameService.game.role = "DEF";
            		GameService.game.roleTxt = "Défenseur";
            	}
            	//vm.personnages = result;
        		vm.searchQuery = null;
            });
        }
    }
})();
