'use strict';

describe('Controller Tests', function() {

    describe('QuestionAnswerTheme Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockQuestionAnswerTheme, MockTheme, MockQuestionAnswer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockQuestionAnswerTheme = jasmine.createSpy('MockQuestionAnswerTheme');
            MockTheme = jasmine.createSpy('MockTheme');
            MockQuestionAnswer = jasmine.createSpy('MockQuestionAnswer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'QuestionAnswerTheme': MockQuestionAnswerTheme,
                'Theme': MockTheme,
                'QuestionAnswer': MockQuestionAnswer
            };
            createController = function() {
                $injector.get('$controller')("QuestionAnswerThemeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hack47App:questionAnswerThemeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
