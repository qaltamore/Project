(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('ChooseYourCampController', ChooseYourCampController);

    ChooseYourCampController.$inject = ['$scope', '$rootScope', '$stateParams', '$state', 'GameService'];

    function ChooseYourCampController ($scope, $rootScope, $stateParams, $state, GameService) {
        var vm = this;
        
        setRole();
        
        function setRole() {
        	var unsubscribe = $rootScope.$on('jHipsterAppliApp:playerUpdate', function(event) {
            });
            $scope.$on('$destroy', unsubscribe);
        }
        
        vm.chooseAttaquant = function() {
        	GameService.game.role = "atk";
        	GameService.game.roleTxt = "Attaquant";
        	$state.go('game');
        };
        vm.chooseDefenseur = function() {
        	GameService.game.role = "def";
        	GameService.game.roleTxt = "Défenseur";
        	$state.go('game');
        };
    }
})();
