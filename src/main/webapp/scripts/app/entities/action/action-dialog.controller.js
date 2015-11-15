'use strict';

angular.module('gtmpaApp').controller('ActionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Action', 'Plan',
        function($scope, $stateParams, $modalInstance, entity, Action, Plan) {

        $scope.action = entity;
        $scope.plans = Plan.query();
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
                Action.update($scope.action, onSaveSuccess, onSaveError);
            } else {
                Action.save($scope.action, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
