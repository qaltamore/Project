(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('team', {
            parent: 'app',
            url: '/team',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/team/team.html',
                    controller: 'TeamController',
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
        .state('team.edit', {
            parent: 'team',
            url: '/{id}/editTeam',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/team/personnage2-dialog.html',
                    controller: 'Personnage2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personnage', function(Personnage) {
                            return Personnage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('team', null, { reload : 'team'});
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
