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
        
        function changeRole() {
        	if(vm.game.role == "atk") {
        		GameService.game.role = "def";
        		GameService.game.roleTxt = "Défenseur";
        	} else {
        		GameService.game.role = "atk";
        		GameService.game.roleTxt = "Attaquant";
        	}
        	$state.reload();
        }
    }
})();
