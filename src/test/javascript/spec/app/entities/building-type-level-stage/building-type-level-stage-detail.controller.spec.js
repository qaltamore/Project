'use strict';

describe('Controller Tests', function() {

    describe('BuildingTypeLevelStage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBuildingTypeLevelStage, MockLevelStage, MockBuildingType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBuildingTypeLevelStage = jasmine.createSpy('MockBuildingTypeLevelStage');
            MockLevelStage = jasmine.createSpy('MockLevelStage');
            MockBuildingType = jasmine.createSpy('MockBuildingType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BuildingTypeLevelStage': MockBuildingTypeLevelStage,
                'LevelStage': MockLevelStage,
                'BuildingType': MockBuildingType
            };
            createController = function() {
                $injector.get('$controller')("BuildingTypeLevelStageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hack47App:buildingTypeLevelStageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
