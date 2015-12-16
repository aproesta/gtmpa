'use strict';

angular.module('gtmpaApp').controller('PlanmilestoneDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Planmilestone', 'Plan',
        function($scope, $stateParams, $modalInstance, entity, Planmilestone, Plan) {

        $scope.planmilestone = entity;
        $scope.plans = Plan.query();
        
        $scope.openMilestoneDate = function($event) {
    		$scope.status.opened = true;
    	};
    	$scope.status = {
    		opened: false
    	};

        
        $scope.load = function(id) {
        	
            Planmilestone.get({id : id}, function(result) {
                $scope.planmilestone = result;
            });
        };
        
        var onSaveSuccess = function (result) {
            $scope.$emit('gtmpaApp:planmilestoneUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.planmilestone.id != null) {
                Planmilestone.update($scope.planmilestone, onSaveSuccess, onSaveError);
            } else {
                Planmilestone.save($scope.planmilestone, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
