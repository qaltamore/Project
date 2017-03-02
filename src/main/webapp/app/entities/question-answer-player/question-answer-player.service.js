(function() {
    'use strict';
    angular
        .module('hack47App')
        .factory('QuestionAnswerPlayer', QuestionAnswerPlayer);

    QuestionAnswerPlayer.$inject = ['$resource'];

    function QuestionAnswerPlayer ($resource) {
        var resourceUrl =  'api/question-answer-players/:id';

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
