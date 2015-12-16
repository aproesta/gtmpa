'use strict';

describe('Planmilestone Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPlanmilestone, MockPlan;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPlanmilestone = jasmine.createSpy('MockPlanmilestone');
        MockPlan = jasmine.createSpy('MockPlan');
        
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Planmilestone': MockPlanmilestone,
            'Plan': MockPlan
        };
        createController = function() {
            $injector.get('$controller')("PlanmilestoneDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gtmpaApp:planmilestoneUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
