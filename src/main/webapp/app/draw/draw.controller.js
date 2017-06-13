(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('DrawController', DrawController);

    DrawController.$inject = ['$scope', '$state', 'Theme', 'QuestionAnswerPlayer', 'QuestionAnswer', 'QuestionAnswerTheme', 'Player' ,'GameService'];

    function DrawController ($scope, $state, Theme, QuestionAnswerPlayer, QuestionAnswer, QuestionAnswerTheme, Player, GameService) {
        var vm = this;

        vm.game = GameService.game;
        vm.themes = [];
        vm.cards = [];
        vm.cardsDB = [];
        vm.cardsInHands = [];
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
        		vm.cardsInHands = result;
                //On parcours la liste des cartes dans une main
        		for(var i = 0; i < result.length; i++) {
        			//On vérifie si elles appartiennent à la bonne personne en vérifiant les roles (ATK ou DEF)
        			if(result[i].player.role == vm.game.role) {
        				//console.log(result[i]);
        				vm.cards.push(result[i].questionAnswer);
            		}
        		}
                vm.searchQuery = null;
            });
        }
        
        function addCardInHand(idTheme) {
        	if(vm.cards.length >= 3)
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
        		
        		//On mélange les cartes récupérée de la BD
        		shuffle(vm.cardsDB);
        		
        		//On fait une liste qui contient les ID des cartes qu'on a dans la main pour ne pas avoir de doublons
        		var idQAInHand = [];
        		for(var i = 0; i < vm.cardsInHands.length; i++) {
        			idQAInHand.push(vm.cardsInHands[i].questionAnswer.id);
        			//console.log(vm.cardsInHands[i].questionAnswer.id);
        		}
        		
        		//On récupère le joueur actuel
        		var player = null;
        		for(i = 0; i < vm.players.length; i++) {
        			if(vm.game.role == "ATK" && vm.players[i].id == 2) {
        				player = vm.players[i];
        			} else if(vm.game.role == "DEF" && vm.players[i].id == 3) {
        				player = vm.players[i];
        			}
        		}
        		
        		var qa = null;
        		
        		//On récupère les questions du thème sélectionné
        		for(i = 0; i < vm.cardsDB.length; i++) {
        			//console.log(vm.cardsDB[i].theme.id + " = " + idTheme + "\n" + vm.cardsDB[i].questionAnswer.level + " = " + level);
        			if(vm.cardsDB[i].theme.id == idTheme && vm.cardsDB[i].questionAnswer.level == level && !idQAInHand.includes(vm.cardsDB[i].questionAnswer.id)) {
        				qa = new QuestionAnswerPlayer();
        				qa.player = player;
        				qa.questionAnswer = vm.cardsDB[i].questionAnswer;
        				QuestionAnswerPlayer.save(qa, function() {vm.cards.push(vm.cardsDB[i].questionAnswer); vm.cardsInHands.push(vm.cardsDB[i]); return;}, function() {console.log("Erreur d'insertion de la carte dans la table QuestionAnswerPlayer"); return;});
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
