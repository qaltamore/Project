(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('GameController', GameController);

    GameController.$inject = ['$scope', 'Principal', 'GameService', '$state'];

    function GameController ($scope, Principal, GameService, $state) {
        var vm = this;
        vm.game = GameService.game;
        
    }
})();
