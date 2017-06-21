(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'GameService'];

    function HomeController ($scope, Principal, LoginService, $state, GameService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });
        
        //réinitialisation du jeu
        GameService.game.role = "ATK";
        GameService.game.roleTxt = "Attaquant";
        GameService.turn = 1;
        localStorage.setItem("gameStorage", JSON.stringify(GameService.game));
        localStorage.setItem("turnStorage", JSON.stringify(GameService.turn));
        
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
