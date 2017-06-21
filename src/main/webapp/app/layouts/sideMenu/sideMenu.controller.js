(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('SideMenuController', SideMenuController);

    SideMenuController.$inject = ['$stateParams', '$state', 'GameService'];

    function SideMenuController ($stateParams, $state, GameService) {
        var vm = this;
        
        //vm.game = GameService.game;
        if(localStorage.getItem("gameStorage") == null)
        	localStorage.setItem("gameStorage", JSON.stringify(GameService.game));
        vm.game = JSON.parse(localStorage.getItem("gameStorage"));
        
        vm.changeRole = changeRole;
        
        if(localStorage.getItem("turnStorage") == null)
        	localStorage.setItem("turnStorage", JSON.stringify(GameService.turn));
        vm.turn = JSON.parse(localStorage.getItem("turnStorage"));
        //vm.turn = GameService.turn;
        vm.increaseTurn = increaseTurn;
        
        function changeRole() {
        	if(vm.game.role == "ATK") {
        		GameService.game.role = "DEF";
        		GameService.game.roleTxt = "Défenseur";
        	} else {
        		GameService.game.role = "ATK";
        		GameService.game.roleTxt = "Attaquant";
        	}
        	localStorage.setItem("gameStorage", JSON.stringify(GameService.game));
        	$state.reload();
        }
        
        function increaseTurn() {
        	GameService.turn = vm.turn + 1;
        	localStorage.setItem("turnStorage", JSON.stringify(GameService.turn));
        	$state.reload();
        }
    }
})();
