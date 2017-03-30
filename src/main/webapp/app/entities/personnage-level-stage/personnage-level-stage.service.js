(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .factory('PersonnageLevelStage', PersonnageLevelStage);

    PersonnageLevelStage.$inject = ['$resource'];

    function PersonnageLevelStage ($resource) {
        var resourceUrl =  'api/personnage-level-stages/:id';

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
