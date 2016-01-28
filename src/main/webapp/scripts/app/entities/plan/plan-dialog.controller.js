'use strict';

angular.module('gtmpaApp').controller('PlanDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Plan', 'Partner', 'Rule', 'Principal',
        function($scope, $stateParams, $modalInstance, entity, Plan, Partner, Rule, Principal) {

	 Principal.identity().then(function(account) {
		 
	    $scope.account = account;

        $scope.plan = entity;
        $scope.partners = Partner.query();
        $scope.allRules = Rule.state({currentState: 'New'});
        $scope.load = function(id) {
        	if (id) {
        		Plan.get({id : id}, function(result) {
	                $scope.plan = result;
	                // returns only valid transition states based on current state 
	                $scope.allRules = Rule.state({currentState: $scope.plan.status});
	            });
        	}
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
            $scope.plan.lastModified = new Date();
            $scope.plan.lastModifiedBy = "@" + $scope.account.login;
            if ($scope.plan.id != null) {
                Plan.update($scope.plan, onSaveSuccess, onSaveError);
            } else {
            	$scope.plan.creationDate = new Date();
                Plan.save($scope.plan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.comment = {comment: ''};
        
        $scope.newComment = function() {
        	var currentComment = $scope.plan.comments || '';
        	if ($scope.comment.comment) {
        		$scope.plan.comments = new Date().toUTCString() + " @" + $scope.account.login + "\n" + $scope.comment.comment + "\n\n" + currentComment;
        		$scope.comment.comment = '';
        	}
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
    	    	
    	/*
    	 * Charts
    	 */
        $scope.c1_labels = ["January", "February", "March", "April", "May", "June", "July"];
        $scope.c1_series = ['Goal', 'Recognised'];
        $scope.c1_data = [
          [65, 59, 80, 81, 56, 55, 40],
          [28, 48, 40, 19, 86, 27, 90]
        ];
        $scope.c1_onClick = function (points, evt) {
          console.log(points, evt);
        };
        
        $scope.c2_labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
        $scope.c2_series = ['Solution', 'Market'];

        $scope.c2_data = [
          [65, 59, 80, 81, 56, 55, 40],
          [28, 48, 40, 19, 86, 27, 90]
        ];
        
        $scope.c3_labels = ["Telco1", "Telco2", "Telco3"];
        $scope.c3_data = [300, 500, 100];

        $scope.load($stateParams.id);
	});

}]);
