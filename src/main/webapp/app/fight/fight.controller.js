(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('FightController', FightController);

    FightController.$inject = ['$scope', '$state', 'Player', 'Personnage', 'GameService', 'FightService'];

    function FightController ($scope, $state, Player, Personnage, GameService, FightService) {
        var vm = this;

        vm.game = GameService.game;
        vm.players = [];
        vm.atkSelected = FightService.atk;
        vm.defSelected = FightService.def;
        
        vm.actionSelected = actionSelected;
        vm.fightLaunched = fightLaunched;
        vm.atkActionSelected = null; //Permet de savoir si l'action de l'attaquant a été sélectionnée
        vm.defActionSelected = null; //Permet de savoir si l'action du défenseur a été sélectionnée
        
        function actionSelected(action) {
        	
        	if(((action == "basicActionAtk" || action == "speActionAtk") && vm.atkActionSelected == null)) {
        		vm.atkActionSelected = action;
        	} else if((action == "basicActionAtk" || action == "speActionAtk") && vm.atkActionSelected != null) {
        		alert("L'action de l'attaquant a déjà été choisie");
        	}
        	
        	if(((action == "basicActionDef" || action == "speActionDef") && vm.defActionSelected == null)) {
        		if(action == "speActionDef" && (vm.defSelected.name == "Bluebot" || vm.defSelected.name == "Redbot" || vm.defSelected.name == "Greenbot")) {
        			alert("Ce robot ne possède pas d'attaque spéciale. Veuillez sélectionner une attaque basique.");
        		} else {
        			vm.defActionSelected = action;
        		}
        	} else if((action == "basicActionDef" || action == "speActionDef") && vm.defActionSelected != null) {
        		alert("L'action du défenseur a déjà été choisie");
        	}
        }
        
        function fightLaunched() {
        	if(vm.atkActionSelected == "basicActionAtk") {
        		var atk = vm.atkSelected.attackPoints;
        		//console.log("Attaque : " + atk);
        		var def = vm.defSelected.defensePoints;
        		//console.log("Défense : " + def);
        		var lifeDef = vm.defSelected.lifePoints;
        		//console.log("Vie Défenseur Avant Fight : " + lifeDef);
        		var damages = 0;
        		var protection = 0;
        		var res = 0;
        		
        		for(var i = atk; i > 0; i--) {
        			res = Math.floor((Math.random()*6));
        			//On définit deux valeurs aléatoirement (il s'agit des faces bleues des dés)
        			if(res != 1 && res != 3) {
        				damages++;
        			}
        		}
        		
        		for(var i = def; i > 0; i--) {
        			res = Math.floor((Math.random()*2));//On attend 0 ou 1 parce qu'on a une chance sur deux de sortir une face bleue
        			//On définit une valeur aléatoirement
        			if(res == 0) {
        				protection++;
        			}
        		}
        		
        		//console.log("Damages : " + damages + " & Protection : " + protection);
        		
        		res = damages - protection;
        		if(res < 0)
        			res = 0;
        		
        		lifeDef = lifeDef - res;
        		
        		//console.log("Vie Défenseur Après Fight : " + lifeDef);
        	}
        }
    }
})();
