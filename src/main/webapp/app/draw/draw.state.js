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
        });
    }

})();
