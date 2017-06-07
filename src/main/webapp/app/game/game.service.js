
//Cette page sert à faire des appels 

(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .service('GameService', GameService);

    GameService.$inject = [];

    function GameService () {
        var self = this;

        self.game = {
        	role : "ATK",
        	roleTxt : "Attaquant"
        };
        
        self.turn = 1;
    }
})();
