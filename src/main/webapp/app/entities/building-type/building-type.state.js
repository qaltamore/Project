(function() {
    'use strict';

    angular
        .module('hack47App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('building-type', {
            parent: 'entity',
            url: '/building-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hack47App.buildingType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building-type/building-types.html',
                    controller: 'BuildingTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('buildingType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('building-type-detail', {
            parent: 'entity',
            url: '/building-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hack47App.buildingType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building-type/building-type-detail.html',
                    controller: 'BuildingTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('buildingType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BuildingType', function($stateParams, BuildingType) {
                    return BuildingType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'building-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('building-type-detail.edit', {
            parent: 'building-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type/building-type-dialog.html',
                    controller: 'BuildingTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BuildingType', function(BuildingType) {
                            return BuildingType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building-type.new', {
            parent: 'building-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type/building-type-dialog.html',
                    controller: 'BuildingTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('building-type', null, { reload: 'building-type' });
                }, function() {
                    $state.go('building-type');
                });
            }]
        })
        .state('building-type.edit', {
            parent: 'building-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type/building-type-dialog.html',
                    controller: 'BuildingTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BuildingType', function(BuildingType) {
                            return BuildingType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building-type', null, { reload: 'building-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building-type.delete', {
            parent: 'building-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type/building-type-delete-dialog.html',
                    controller: 'BuildingTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BuildingType', function(BuildingType) {
                            return BuildingType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building-type', null, { reload: 'building-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
