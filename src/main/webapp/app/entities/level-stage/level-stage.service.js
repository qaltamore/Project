(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .factory('LevelStage', LevelStage);

    LevelStage.$inject = ['$resource'];

    function LevelStage ($resource) {
        var resourceUrl =  'api/level-stages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
