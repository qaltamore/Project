(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('selectCharacter', {
            parent: 'app',
            url: '/selectCharacter',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/fight/selectCharacter.html',
                    controller: 'SelectCharacterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
