(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('building-type-level-stage', {
            parent: 'entity',
            url: '/building-type-level-stage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.buildingTypeLevelStage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building-type-level-stage/building-type-level-stages.html',
                    controller: 'BuildingTypeLevelStageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('buildingTypeLevelStage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('building-type-level-stage-detail', {
            parent: 'entity',
            url: '/building-type-level-stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.buildingTypeLevelStage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building-type-level-stage/building-type-level-stage-detail.html',
                    controller: 'BuildingTypeLevelStageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('buildingTypeLevelStage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BuildingTypeLevelStage', function($stateParams, BuildingTypeLevelStage) {
                    return BuildingTypeLevelStage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'building-type-level-stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('building-type-level-stage-detail.edit', {
            parent: 'building-type-level-stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type-level-stage/building-type-level-stage-dialog.html',
                    controller: 'BuildingTypeLevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BuildingTypeLevelStage', function(BuildingTypeLevelStage) {
                            return BuildingTypeLevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building-type-level-stage.new', {
            parent: 'building-type-level-stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type-level-stage/building-type-level-stage-dialog.html',
                    controller: 'BuildingTypeLevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                buildingsQuantity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('building-type-level-stage', null, { reload: 'building-type-level-stage' });
                }, function() {
                    $state.go('building-type-level-stage');
                });
            }]
        })
        .state('building-type-level-stage.edit', {
            parent: 'building-type-level-stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type-level-stage/building-type-level-stage-dialog.html',
                    controller: 'BuildingTypeLevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BuildingTypeLevelStage', function(BuildingTypeLevelStage) {
                            return BuildingTypeLevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building-type-level-stage', null, { reload: 'building-type-level-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building-type-level-stage.delete', {
            parent: 'building-type-level-stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building-type-level-stage/building-type-level-stage-delete-dialog.html',
                    controller: 'BuildingTypeLevelStageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BuildingTypeLevelStage', function(BuildingTypeLevelStage) {
                            return BuildingTypeLevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building-type-level-stage', null, { reload: 'building-type-level-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
