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
            	if(vm.game.role == "atk") {
            		var sliced = result.slice(0, 4);
            		vm.personnages = sliced;
            		GameService.game.role = "atk";
            		GameService.game.roleTxt = "Attaquant";
            	} else {
            		var sliced = result.slice(4, -1);
            		vm.personnages = sliced;
            		GameService.game.role = "def";
            		GameService.game.roleTxt = "Défenseur";
            	}
        		vm.searchQuery = null;
            });
        }
    }
})();
