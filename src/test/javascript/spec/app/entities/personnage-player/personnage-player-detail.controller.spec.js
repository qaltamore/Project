'use strict';

describe('Controller Tests', function() {

    describe('PersonnagePlayer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPersonnagePlayer, MockPlayer, MockPersonnage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPersonnagePlayer = jasmine.createSpy('MockPersonnagePlayer');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockPersonnage = jasmine.createSpy('MockPersonnage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PersonnagePlayer': MockPersonnagePlayer,
                'Player': MockPlayer,
                'Personnage': MockPersonnage
            };
            createController = function() {
                $injector.get('$controller')("PersonnagePlayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jHipsterAppliApp:personnagePlayerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
