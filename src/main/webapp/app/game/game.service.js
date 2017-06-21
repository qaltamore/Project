
//Cette page sert à faire des appels 

(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .service('GameService', GameService);

    GameService.$inject = ['Personnage'];

    function GameService (Personnage) {
        var self = this;

        self.game = {
        	role : "ATK",
        	roleTxt : "Attaquant"
        };
        
        self.turn = 1;
        
        self.listePersonnages = new Array();
        self.listePersonnages["FulmiNightPV"] = 15;
        self.listePersonnages["FulmiNightMANA"] = 2;
        self.listePersonnages["RobertPV"] = 15;
        self.listePersonnages["RobertMANA"] = 2;
        self.listePersonnages["BilogzPV"] = 10;
        self.listePersonnages["BilogzMANA"] = 4;
        self.listePersonnages["NartPV"] = 10;
        self.listePersonnages["NartMANA"] = 6;
        self.listePersonnages["BluebotPV"] = 6;
        self.listePersonnages["BluebotMANA"] = 0;
        self.listePersonnages["RedbotPV"] = 10;
        self.listePersonnages["RedbotMANA"] = 0;
        self.listePersonnages["GreenbotPV"] = 8;
        self.listePersonnages["GreenbotMANA"] = 0;
        self.listePersonnages["GraybotPV"] = 4;
        self.listePersonnages["GraybotMANA"] = 4;
        self.listePersonnages["BossbotPV"] = 15;
        self.listePersonnages["BossbotMANA"] = 2;
    }
})();
