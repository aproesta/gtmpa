'use strict';

angular.module('gtmpaApp').controller('PlanDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Plan', 'Partner',
        function($scope, $stateParams, $modalInstance, entity, Plan, Partner) {

        $scope.plan = entity;
        $scope.partners = Partner.query();
        $scope.load = function(id) {
            Plan.get({id : id}, function(result) {
                $scope.plan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gtmpaApp:planUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.plan.id != null) {
                Plan.update($scope.plan, onSaveSuccess, onSaveError);
            } else {
                Plan.save($scope.plan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        // TODO Calculate this from the Rules
        $scope.minGTMDateDays = 100;
        
        $scope.getMinGTMDate = function() {
        	var today = new Date();

        	today.setDate(today.getDate() + $scope.minGTMDateDays);
        	
        	return today;

        };

    	$scope.minDate = $scope.getMinGTMDate();

}]);
