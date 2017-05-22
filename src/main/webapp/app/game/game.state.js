(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('game', {
	        parent: 'app',
	        url: '/game',
	        data: {
	            authorities: ['ROLE_USER'],
	            pageTitle: 'jHipsterAppliApp.personnage.game.title'
	        },
	        views: {
	            'content@': {
	                templateUrl: 'app/game/game.html',
	                controller: 'GameController',
	                controllerAs: 'vm'
	            }
	        },
	        resolve: {
	            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                $translatePartialLoader.addPart('personnage');
	                $translatePartialLoader.addPart('type');
	                $translatePartialLoader.addPart('global');
	                return $translate.refresh();
	            }]
	        }
        })
        .state('game.edit', {
            parent: 'game',
            url: '/{id}/editGame',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/game/personnageGame.html',
                    controller: 'PersonnageGameController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personnage', function(Personnage) {
                            return Personnage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('game', null, { reload : 'game'});
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }
})();
