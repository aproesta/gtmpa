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

        
        /*
         * DatePicker for agreedToMarket
         */
        // TODO Calculate this from the Rules
        $scope.minGTMDateDays = 100;
        $scope.getMinGTMDate = function() {
        	var today = new Date();
        	today.setDate(today.getDate() + $scope.minGTMDateDays);
        	return today;
        };
   	    $scope.aGTMminDate = $scope.getMinGTMDate();
        $scope.plan.agreedGTMDate = $scope.aGTMminDate;

    	$scope.openGTM = function($event) {
    		$scope.statusGTM.opened = true;
    	};
    	$scope.statusGTM = {
    		opened: false
    	};

        /*
         * DatePicker for agreedToMarket
         */
   	    $scope.proposalMinDate = new Date();
        $scope.plan.proposalDate = $scope.proposalMinDate;
    	$scope.openProposal = function($event) {
    		$scope.statusProposal.opened = true;
    	};
    	$scope.statusProposal = {
    		opened: false
    	};
    	
}]);
