(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-answer', {
            parent: 'entity',
            url: '/question-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.questionAnswer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-answer/question-answers.html',
                    controller: 'QuestionAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAnswer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('question-answer-detail', {
            parent: 'entity',
            url: '/question-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.questionAnswer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-answer/question-answer-detail.html',
                    controller: 'QuestionAnswerDetailController',
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
        })
        .state('question-answer-detail.edit', {
            parent: 'question-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer/question-answer-dialog.html',
                    controller: 'QuestionAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAnswer', function(QuestionAnswer) {
                            return QuestionAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-answer.new', {
            parent: 'question-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer/question-answer-dialog.html',
                    controller: 'QuestionAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titre: null,
                                level: null,
                                question: null,
                                proposition1: null,
                                proposition2: null,
                                proposition3: null,
                                proposition4: null,
                                answer: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('question-answer', null, { reload: 'question-answer' });
                }, function() {
                    $state.go('question-answer');
                });
            }]
        })
        .state('question-answer.edit', {
            parent: 'question-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer/question-answer-dialog.html',
                    controller: 'QuestionAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAnswer', function(QuestionAnswer) {
                            return QuestionAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-answer', null, { reload: 'question-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-answer.delete', {
            parent: 'question-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer/question-answer-delete-dialog.html',
                    controller: 'QuestionAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionAnswer', function(QuestionAnswer) {
                            return QuestionAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-answer', null, { reload: 'question-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
