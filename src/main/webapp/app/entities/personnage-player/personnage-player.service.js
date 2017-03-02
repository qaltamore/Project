(function() {
    'use strict';
    angular
        .module('hack47App')
        .factory('PersonnagePlayer', PersonnagePlayer);

    PersonnagePlayer.$inject = ['$resource'];

    function PersonnagePlayer ($resource) {
        var resourceUrl =  'api/personnage-players/:id';

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
