(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-answer-theme', {
            parent: 'entity',
            url: '/question-answer-theme',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.questionAnswerTheme.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-answer-theme/question-answer-themes.html',
                    controller: 'QuestionAnswerThemeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAnswerTheme');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('question-answer-theme-detail', {
            parent: 'entity',
            url: '/question-answer-theme/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jHipsterAppliApp.questionAnswerTheme.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-answer-theme/question-answer-theme-detail.html',
                    controller: 'QuestionAnswerThemeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAnswerTheme');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'QuestionAnswerTheme', function($stateParams, QuestionAnswerTheme) {
                    return QuestionAnswerTheme.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-answer-theme',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-answer-theme-detail.edit', {
            parent: 'question-answer-theme-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-theme/question-answer-theme-dialog.html',
                    controller: 'QuestionAnswerThemeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAnswerTheme', function(QuestionAnswerTheme) {
                            return QuestionAnswerTheme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-answer-theme.new', {
            parent: 'question-answer-theme',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-theme/question-answer-theme-dialog.html',
                    controller: 'QuestionAnswerThemeDialogController',
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
                    $state.go('question-answer-theme', null, { reload: 'question-answer-theme' });
                }, function() {
                    $state.go('question-answer-theme');
                });
            }]
        })
        .state('question-answer-theme.edit', {
            parent: 'question-answer-theme',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-theme/question-answer-theme-dialog.html',
                    controller: 'QuestionAnswerThemeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAnswerTheme', function(QuestionAnswerTheme) {
                            return QuestionAnswerTheme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-answer-theme', null, { reload: 'question-answer-theme' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-answer-theme.delete', {
            parent: 'question-answer-theme',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-answer-theme/question-answer-theme-delete-dialog.html',
                    controller: 'QuestionAnswerThemeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionAnswerTheme', function(QuestionAnswerTheme) {
                            return QuestionAnswerTheme.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-answer-theme', null, { reload: 'question-answer-theme' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
