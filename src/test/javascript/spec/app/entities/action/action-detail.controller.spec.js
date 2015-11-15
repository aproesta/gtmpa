'use strict';

describe('Action Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAction, MockPlan;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAction = jasmine.createSpy('MockAction');
        MockPlan = jasmine.createSpy('MockPlan');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Action': MockAction,
            'Plan': MockPlan
        };
        createController = function() {
            $injector.get('$controller')("ActionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gtmpaApp:actionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
