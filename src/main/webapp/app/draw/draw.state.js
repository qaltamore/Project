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
        })
        .state('draw-details', {
        	parent: 'draw',
            url: '/draw-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.questionAnswerPlayer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/draw/draw-details.html',
                    controller: 'DrawDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAnswer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'QuestionAnswer', function($stateParams, QuestionAnswer) {
                    return QuestionAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }

})();
