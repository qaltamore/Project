(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .factory('BuildingType', BuildingType);

    BuildingType.$inject = ['$resource'];

    function BuildingType ($resource) {
        var resourceUrl =  'api/building-types/:id';

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
