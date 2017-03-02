(function() {
    'use strict';

    angular
        .module('hack47App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('level-stage', {
            parent: 'entity',
            url: '/level-stage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hack47App.levelStage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/level-stage/level-stages.html',
                    controller: 'LevelStageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('levelStage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('level-stage-detail', {
            parent: 'entity',
            url: '/level-stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hack47App.levelStage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/level-stage/level-stage-detail.html',
                    controller: 'LevelStageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('levelStage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LevelStage', function($stateParams, LevelStage) {
                    return LevelStage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'level-stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('level-stage-detail.edit', {
            parent: 'level-stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/level-stage/level-stage-dialog.html',
                    controller: 'LevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LevelStage', function(LevelStage) {
                            return LevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('level-stage.new', {
            parent: 'level-stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/level-stage/level-stage-dialog.html',
                    controller: 'LevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idLevelStage: null,
                                numero: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('level-stage', null, { reload: 'level-stage' });
                }, function() {
                    $state.go('level-stage');
                });
            }]
        })
        .state('level-stage.edit', {
            parent: 'level-stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/level-stage/level-stage-dialog.html',
                    controller: 'LevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LevelStage', function(LevelStage) {
                            return LevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('level-stage', null, { reload: 'level-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('level-stage.delete', {
            parent: 'level-stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/level-stage/level-stage-delete-dialog.html',
                    controller: 'LevelStageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LevelStage', function(LevelStage) {
                            return LevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('level-stage', null, { reload: 'level-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
