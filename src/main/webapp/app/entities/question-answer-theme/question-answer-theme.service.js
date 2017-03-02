(function() {
    'use strict';
    angular
        .module('hack47App')
        .factory('QuestionAnswerTheme', QuestionAnswerTheme);

    QuestionAnswerTheme.$inject = ['$resource'];

    function QuestionAnswerTheme ($resource) {
        var resourceUrl =  'api/question-answer-themes/:id';

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
