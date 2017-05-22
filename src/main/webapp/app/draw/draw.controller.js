(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('DrawController', DrawController);

    DrawController.$inject = ['$scope', '$state', 'Theme', 'QuestionAnswerPlayer', 'GameService'];

    function DrawController ($scope, $state, Theme, QuestionAnswerPlayer, GameService) {
        var vm = this;

        vm.game = GameService.game;
        vm.idPlayer = "";
        if(vm.game.role == "atk") {
        	vm.idPlayer = 1;
        } else {
        	vm.idPlayer = 2;
        }
        vm.themes = [];
        vm.cards = [];
        vm.addCard = addCard;

        loadAllThemes();
        loadAllCardsInHand();

        function loadAllThemes() {
        	Theme.query(function(result) {
                vm.themes = result;
                vm.searchQuery = null;
            });
        }
        
        function loadAllCardsInHand() {
        	QuestionAnswerPlayer.query(function(result) {
                vm.cards = result;
                vm.searchQuery = null;
            });
        }
        
        function addCard(idTheme) {
        }
    }
})();
