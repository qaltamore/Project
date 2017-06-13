(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('FightController', FightController);

    FightController.$inject = ['$scope', '$state', 'Player', 'Personnage', 'GameService'];

    function FightController ($scope, $state, Player, Personnage, GameService) {
        var vm = this;

        vm.game = GameService.game;
        vm.players = [];
    }
})();
