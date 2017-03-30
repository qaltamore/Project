(function() {
    'use strict';
    angular
        .module('jHipsterAppliApp')
        .factory('QuestionAnswer', QuestionAnswer);

    QuestionAnswer.$inject = ['$resource'];

    function QuestionAnswer ($resource) {
        var resourceUrl =  'api/question-answers/:id';

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
