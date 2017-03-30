(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personnage-player', {
            parent: 'entity',
            url: '/personnage-player',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.personnagePlayer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personnage-player/personnage-players.html',
                    controller: 'PersonnagePlayerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personnagePlayer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('personnage-player-detail', {
            parent: 'entity',
            url: '/personnage-player/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.personnagePlayer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personnage-player/personnage-player-detail.html',
                    controller: 'PersonnagePlayerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personnagePlayer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonnagePlayer', function($stateParams, PersonnagePlayer) {
                    return PersonnagePlayer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personnage-player',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personnage-player-detail.edit', {
            parent: 'personnage-player-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-player/personnage-player-dialog.html',
                    controller: 'PersonnagePlayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonnagePlayer', function(PersonnagePlayer) {
                            return PersonnagePlayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personnage-player.new', {
            parent: 'personnage-player',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-player/personnage-player-dialog.html',
                    controller: 'PersonnagePlayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personnage-player', null, { reload: 'personnage-player' });
                }, function() {
                    $state.go('personnage-player');
                });
            }]
        })
        .state('personnage-player.edit', {
            parent: 'personnage-player',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-player/personnage-player-dialog.html',
                    controller: 'PersonnagePlayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonnagePlayer', function(PersonnagePlayer) {
                            return PersonnagePlayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personnage-player', null, { reload: 'personnage-player' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personnage-player.delete', {
            parent: 'personnage-player',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-player/personnage-player-delete-dialog.html',
                    controller: 'PersonnagePlayerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonnagePlayer', function(PersonnagePlayer) {
                            return PersonnagePlayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personnage-player', null, { reload: 'personnage-player' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
