'use strict';

angular.module('gtmpaApp').controller('PlanmilestoneDialogController1',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Planmilestone', 'Plan',
        function($scope, $stateParams, $modalInstance, entity, Planmilestone, Plan) {

        $scope.planmilestone = entity;
        $scope.plans = Plan.query();
       
       //alert($stateParams.id);
       
        $scope.planmilestone.plan = Plan.get({id: $stateParams.id}, function(result) {
        	// alert("1111111111111111111111111");
            $scope.planmilestone.plan = result;
            //alert(result.solutionName);
            $scope.planmilestone.id=null;
        });
       /* 
        $scope.load = function(id) {
        	
        	Plan.get({id : $stateParams.id}, function(result) {
            	$scope.planmilestone.plan = result;
            });
            
        };*/
        
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
            //alert("2222  :" + $scope.planmilestone.plan.id);
            /*if ($scope.planmilestone.id != null) {
                Planmilestone.update($scope.planmilestone, onSaveSuccess, onSaveError);
            } else {*/
            $scope.planmilestone.plan = $scope.planmilestone.plan;
                Planmilestone.save($scope.planmilestone, onSaveSuccess, onSaveError);
            //}
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
