'use strict';

describe('Plan Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPlan, MockPartner;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPlan = jasmine.createSpy('MockPlan');
        MockPartner = jasmine.createSpy('MockPartner');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Plan': MockPlan,
            'Partner': MockPartner
        };
        createController = function() {
            $injector.get('$controller')("PlanDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gtmpaApp:planUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
