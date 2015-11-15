'use strict';

angular.module('gtmpaApp').controller('RuleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Rule',
        function($scope, $stateParams, $modalInstance, entity, Rule) {

        $scope.rule = entity;
        $scope.load = function(id) {
            Rule.get({id : id}, function(result) {
                $scope.rule = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gtmpaApp:ruleUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rule.id != null) {
                Rule.update($scope.rule, onSaveSuccess, onSaveError);
            } else {
                Rule.save($scope.rule, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
