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
        });
    }
})();
