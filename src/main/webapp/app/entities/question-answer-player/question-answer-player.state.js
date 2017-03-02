(function() {
    'use strict';

    angular
        .module('hack47App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-answer-player', {
            parent: 'entity',
            url: '/question-answer-player',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hack47App.questionAnswerPlayer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-answer-player/question-answer-players.html',
                    controller: 'QuestionAnswerPlayerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAnswerPlayer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('question-answer-player-detail', {
            parent: 'entity',
            url: '/question-answer-player/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hack47App.questionAnswerPlayer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-answer-player/question-answer-player-detail.html',
                    controller: 'QuestionAnswerPlayerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAnswerPlayer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'QuestionAnswerPlayer', function($stateParams, QuestionAnswerPlayer) {
                    return QuestionAnswerPlayer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-answer-player',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-answer-player-detail.edit', {
            parent: 'question-answer-player-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-player/question-answer-player-dialog.html',
                    controller: 'QuestionAnswerPlayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAnswerPlayer', function(QuestionAnswerPlayer) {
                            return QuestionAnswerPlayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-answer-player.new', {
            parent: 'question-answer-player',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-player/question-answer-player-dialog.html',
                    controller: 'QuestionAnswerPlayerDialogController',
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
                    $state.go('question-answer-player', null, { reload: 'question-answer-player' });
                }, function() {
                    $state.go('question-answer-player');
                });
            }]
        })
        .state('question-answer-player.edit', {
            parent: 'question-answer-player',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-player/question-answer-player-dialog.html',
                    controller: 'QuestionAnswerPlayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAnswerPlayer', function(QuestionAnswerPlayer) {
                            return QuestionAnswerPlayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-answer-player', null, { reload: 'question-answer-player' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-answer-player.delete', {
            parent: 'question-answer-player',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-player/question-answer-player-delete-dialog.html',
                    controller: 'QuestionAnswerPlayerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionAnswerPlayer', function(QuestionAnswerPlayer) {
                            return QuestionAnswerPlayer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-answer-player', null, { reload: 'question-answer-player' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
