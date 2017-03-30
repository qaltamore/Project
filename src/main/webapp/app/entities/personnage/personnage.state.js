(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personnage', {
            parent: 'entity',
            url: '/personnage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.personnage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personnage/personnages.html',
                    controller: 'PersonnageController',
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
        .state('personnage-detail', {
            parent: 'entity',
            url: '/personnage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.personnage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personnage/personnage-detail.html',
                    controller: 'PersonnageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personnage');
                    $translatePartialLoader.addPart('type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Personnage', function($stateParams, Personnage) {
                    return Personnage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personnage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personnage-detail.edit', {
            parent: 'personnage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage/personnage-dialog.html',
                    controller: 'PersonnageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personnage', function(Personnage) {
                            return Personnage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personnage.new', {
            parent: 'personnage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage/personnage-dialog.html',
                    controller: 'PersonnageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                lifePoints: null,
                                movementPoints: null,
                                attackPoints: null,
                                defensePoints: null,
                                magicPoints: null,
                                capacity: null,
                                inLive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personnage', null, { reload: 'personnage' });
                }, function() {
                    $state.go('personnage');
                });
            }]
        })
        .state('personnage.edit', {
            parent: 'personnage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage/personnage-dialog.html',
                    controller: 'PersonnageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Personnage', function(Personnage) {
                            return Personnage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personnage', null, { reload: 'personnage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personnage.delete', {
            parent: 'personnage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage/personnage-delete-dialog.html',
                    controller: 'PersonnageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Personnage', function(Personnage) {
                            return Personnage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personnage', null, { reload: 'personnage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
