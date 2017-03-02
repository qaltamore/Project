(function() {
    'use strict';
    angular
        .module('hack47App')
        .factory('BuildingTypeLevelStage', BuildingTypeLevelStage);

    BuildingTypeLevelStage.$inject = ['$resource'];

    function BuildingTypeLevelStage ($resource) {
        var resourceUrl =  'api/building-type-level-stages/:id';

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
