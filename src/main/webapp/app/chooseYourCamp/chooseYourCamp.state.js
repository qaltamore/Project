(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('chooseYourCamp', {
            //parent: 'app',
            url: '/chooseYourCamp',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/chooseYourCamp/chooseYourCamp.html',
                    controller: 'ChooseYourCampController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
