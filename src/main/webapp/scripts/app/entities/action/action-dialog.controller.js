'use strict';

angular.module('gtmpaApp').controller('ActionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Action', 'Plan','User',
        function($scope, $stateParams, $modalInstance, entity, Action, Plan, User) {

        $scope.action = entity;
        $scope.plans = Plan.query();
        $scope.load = function(id) {
            Action.get({id : id}, function(result) {
                $scope.action = result;
            });
        };
        
        $scope.users = User.query();
        $scope.load = function(id) {
            Action.get({id : id}, function(result) {
                $scope.action = result;
            });
        };
        var onSaveSuccess = function (result) {
            $scope.$emit('gtmpaApp:actionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.action.id != null) {
            	$scope.action.status = "Pending"
                Action.update($scope.action, onSaveSuccess, onSaveError);
            } else {
                Action.save($scope.action, onSaveSuccess, onSaveError);
            }
        };
        //naresh
        $scope.markcompleted = function () {
           
        	$scope.isSaving = true;
            $scope.action.status = "Completed"
            Action.save($scope.action, onSaveSuccess, onSaveError);
          
        };
        
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        
     //naresh
        $(document).ready( function() {
        	
        	//alert("I am an alert box Actionsdialogcontrolle");        	  
            $scope.action.logDate = new Date();
            //$('#nextActionDate').val(today);
        });
}]);

