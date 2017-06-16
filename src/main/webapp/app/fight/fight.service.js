
//Cette page sert à faire des appels 

(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .service('FightService', FightService);

    FightService.$inject = [];

    function FightService () {
        var self = this;

        self.atk = null;
        self.def = null;
    }
})();
