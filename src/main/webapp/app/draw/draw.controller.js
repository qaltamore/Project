(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('DrawController', DrawController);

    DrawController.$inject = ['$scope', '$state', 'Theme', 'QuestionAnswerPlayer', 'QuestionAnswerTheme', 'Player' ,'GameService'];

    function DrawController ($scope, $state, Theme, QuestionAnswerPlayer, QuestionAnswerTheme, Player, GameService) {
        var vm = this;

        vm.game = GameService.game;
        vm.themes = [];
        vm.cards = [];
        vm.cardsDB = [];
        vm.players = [];
        vm.addCardInHand = addCardInHand;

        loadAllThemes();
        loadAllCardsInHand();

        function loadAllThemes() {
        	Theme.query(function(result) {
                vm.themes = result;
                vm.searchQuery = null;
            });
        }
        
        function loadAllCardsInHand() {
        	QuestionAnswerTheme.query(function(result) {
        		vm.cardsDB = result;
        	});
        	
        	Player.query(function(result) {
        		vm.players = result;
        	});
        	
        	QuestionAnswerPlayer.query(function(result) {
                //On parcours la liste des cartes dans une main
        		for(var i = 0; i < result.length; i++) {
        			//On vérifie si elles appartiennent à la bonne personne en vérifiant les roles (ATK ou DEF)
        			if(result[i].player.role == vm.game.role) {
        				console.log(result[i]);
        				vm.cards.push(result[i].questionAnswer);
            		}
        		}

                vm.searchQuery = null;
            });
        }
        
        function addCardInHand(idTheme) {
        	if(vm.cards.length >= 5)
        		return;
        	else {
        		//On définit le niveau des questions
        		var level = 1;
        		if(vm.game.turn > 10 && vm.game.turn <= 20) {
        			level = 2;
        		} else if(vm.game.turn > 20 && vm.game.turn <= 30) {
        			level = 2;
        		} else if(vm.game.turn > 30 && vm.game.turn <= 40) {
        			level = 3;
        		} else if(vm.game.turn > 40 && vm.game.turn <= 50) {
        			level = 4;
        		} else if(vm.game.turn > 50) {
        			level = 5;
        		}
        		
        		shuffle(vm.cardsDB);
        		
        		//On fait une liste qui contient les ID des cartes qu'on a dans la main pour ne pas avoir de doublons
        		var idQAInHand = [];
        		for(var i = 0; i < vm.cards.length; i++) {
        			idQAInHand.push(vm.cards[i].id);
        		}
        		
        		var player = null;
        		for(i = 0; i < vm.players.length; i++) {
        			if(vm.game.role == "ATK" && vm.players[i].id == 2) {
        				player = vm.players[i];
        			} else if(vm.game.role == "DEF" && vm.players[i].id == 3) {
        				player = vm.players[i];
        			}
        		}
        		
        		//On récupère les questions du thème sélectionné
        		for(i = 0; i < vm.cardsDB.length; i++) {
        			//console.log(vm.cardsDB[i].theme.id + " = " + idTheme + "\n" + vm.cardsDB[i].questionAnswer.level + " = " + level);
        			if(vm.cardsDB[i].theme.id == idTheme && vm.cardsDB[i].questionAnswer.level == level && !idQAInHand.includes(vm.cardsDB[i].questionAnswer.id)) {
        				vm.cards.push(vm.cardsDB[i].questionAnswer);
        				QuestionAnswerPlayer.save(vm.cardsDB[i].questionAnswer);
        				return;
        			}
        		}
        	}
        }
        
        function shuffle(a) {
            var j, x, i;
            for (i = a.length; i; i--) {
                j = Math.floor(Math.random() * i);
                x = a[i - 1];
                a[i - 1] = a[j];
                a[j] = x;
            }
        }
    }
})();
