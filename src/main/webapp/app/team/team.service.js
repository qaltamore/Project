(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .factory('Personnage', Personnage);

    Personnage.$inject = ['$resource'];

    function Personnage ($resource) {
        var resourceUrl =  'api/personnages/:id';

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
