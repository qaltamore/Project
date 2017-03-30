(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('personnage-level-stage', {
            parent: 'entity',
            url: '/personnage-level-stage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.personnageLevelStage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personnage-level-stage/personnage-level-stages.html',
                    controller: 'PersonnageLevelStageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personnageLevelStage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('personnage-level-stage-detail', {
            parent: 'entity',
            url: '/personnage-level-stage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.personnageLevelStage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/personnage-level-stage/personnage-level-stage-detail.html',
                    controller: 'PersonnageLevelStageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('personnageLevelStage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PersonnageLevelStage', function($stateParams, PersonnageLevelStage) {
                    return PersonnageLevelStage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'personnage-level-stage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('personnage-level-stage-detail.edit', {
            parent: 'personnage-level-stage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-level-stage/personnage-level-stage-dialog.html',
                    controller: 'PersonnageLevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonnageLevelStage', function(PersonnageLevelStage) {
                            return PersonnageLevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personnage-level-stage.new', {
            parent: 'personnage-level-stage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-level-stage/personnage-level-stage-dialog.html',
                    controller: 'PersonnageLevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                personnagesQuantity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('personnage-level-stage', null, { reload: 'personnage-level-stage' });
                }, function() {
                    $state.go('personnage-level-stage');
                });
            }]
        })
        .state('personnage-level-stage.edit', {
            parent: 'personnage-level-stage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-level-stage/personnage-level-stage-dialog.html',
                    controller: 'PersonnageLevelStageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PersonnageLevelStage', function(PersonnageLevelStage) {
                            return PersonnageLevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personnage-level-stage', null, { reload: 'personnage-level-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('personnage-level-stage.delete', {
            parent: 'personnage-level-stage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/personnage-level-stage/personnage-level-stage-delete-dialog.html',
                    controller: 'PersonnageLevelStageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PersonnageLevelStage', function(PersonnageLevelStage) {
                            return PersonnageLevelStage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('personnage-level-stage', null, { reload: 'personnage-level-stage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
