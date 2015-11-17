'use strict';

angular.module('gtmpaApp').controller('UserManagementDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'User',
        function($scope, $stateParams, $modalInstance, entity, User) {

        $scope.user = entity;
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN", "ROLE_BP", "ROLE_BP_MANAGER", "ROLE_CHANNEL_MANAGER", "ROLE_BUS_OPS"];
        var onSaveSuccess = function (result) {
            $scope.isSaving = false;
            $modalInstance.close(result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.user.id != null) {
                User.update($scope.user, onSaveSuccess, onSaveError);
            } else {
                User.save($scope.user, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
