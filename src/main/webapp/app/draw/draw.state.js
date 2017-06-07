(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('draw', {
            parent: 'app',
            url: '/draw',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/draw/draw.html',
                    controller: 'DrawController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('theme');
                    $translatePartialLoader.addPart('questionAnswerPlayer');
                    $translatePartialLoader.addPart('type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('draw.add', {
            parent: 'draw',
            url: '/{id}/add',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/draw/draw.html',
                    controller: 'DrawController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Theme', function(Theme) {
                            return Theme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('draw', null, { reload: 'draw' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
