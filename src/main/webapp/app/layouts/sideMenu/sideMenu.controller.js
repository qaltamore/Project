(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('SideMenuController', SideMenuController);

    SideMenuController.$inject = ['$stateParams', '$state', 'GameService'];

    function SideMenuController ($stateParams, $state, GameService) {
        var vm = this;
        
        vm.game = GameService.game;
        vm.changeRole = changeRole;
        vm.turn = GameService.turn;
        vm.increaseTurn = increaseTurn;
        
        function changeRole() {
        	if(vm.game.role == "ATK") {
        		GameService.game.role = "DEF";
        		GameService.game.roleTxt = "Défenseur";
        	} else {
        		GameService.game.role = "ATK";
        		GameService.game.roleTxt = "Attaquant";
        	}
        	$state.reload();
        }
        
        function increaseTurn() {
        	GameService.turn = GameService.turn + 1;
        	$state.reload();
        }
    }
})();
