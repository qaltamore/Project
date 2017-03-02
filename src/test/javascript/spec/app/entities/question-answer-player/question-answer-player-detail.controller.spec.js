'use strict';

describe('Controller Tests', function() {

    describe('QuestionAnswerPlayer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockQuestionAnswerPlayer, MockPlayer, MockQuestionAnswer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockQuestionAnswerPlayer = jasmine.createSpy('MockQuestionAnswerPlayer');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockQuestionAnswer = jasmine.createSpy('MockQuestionAnswer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'QuestionAnswerPlayer': MockQuestionAnswerPlayer,
                'Player': MockPlayer,
                'QuestionAnswer': MockQuestionAnswer
            };
            createController = function() {
                $injector.get('$controller')("QuestionAnswerPlayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hack47App:questionAnswerPlayerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
