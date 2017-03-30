'use strict';

describe('Controller Tests', function() {

    describe('PersonnageLevelStage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonnageLevelStage, MockPersonnage, MockLevelStage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonnageLevelStage = jasmine.createSpy('MockPersonnageLevelStage');
            MockPersonnage = jasmine.createSpy('MockPersonnage');
            MockLevelStage = jasmine.createSpy('MockLevelStage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonnageLevelStage': MockPersonnageLevelStage,
                'Personnage': MockPersonnage,
                'LevelStage': MockLevelStage
            };
            createController = function() {
                $injector.get('$controller')("PersonnageLevelStageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jHipsterAppliApp:personnageLevelStageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
