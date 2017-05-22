(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('GameController', GameController);

    GameController.$inject = ['$scope', 'Principal', 'GameService', 'Personnage', 'QuestionAnswerPlayer', '$state'];

    function GameController ($scope, Principal, GameService, Personnage, QuestionAnswerPlayer, $state) {
        var vm = this;
        
        vm.game = GameService.game;
        
        vm.personnages = [];
        vm.personnagesAttaquant = [];
        vm.personnagesDefenseur = [];
        vm.cards = [];
        vm.changeRole = changeRole;
        
        loadAllPersonnages();
        loadAllCards();
        
        function loadAllPersonnages() {
        	Personnage.query(function(result) {
            	var attaquants = result.slice(0, 4);
                var defenseurs = result.slice(4);

            	vm.personnages = result;
                vm.personnagesAttaquant = attaquants;
                vm.personnagesDefenseur = defenseurs;
        		vm.searchQuery = null;
        	});
        }
        
        function changeRole() {
        	if(vm.game.role == "atk") {
        		GameService.game.role = "def";
        		GameService.game.roleTxt = "Défenseur";
        	} else {
        		GameService.game.role = "atk";
        		GameService.game.roleTxt = "Attaquant";
        	}
        }
        
        function loadAllCards() {
        	QuestionAnswerPlayer.query(function(result) {
        		vm.cards = result;
        		vm.searchQuery = null;
        	});
        }
    }
})();
