'use strict';

angular.module('gtmpaApp').controller('PartneruserlinkDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Partneruserlink',
        function($scope, $stateParams, $modalInstance, entity, Partneruserlink) {

        $scope.partneruserlink = entity;
        $scope.load = function(id) {
            Partneruserlink.get({id : id}, function(result) {
                $scope.partneruserlink = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gtmpaApp:partneruserlinkUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.partneruserlink.id != null) {
                Partneruserlink.update($scope.partneruserlink, onSaveSuccess, onSaveError);
            } else {
                Partneruserlink.save($scope.partneruserlink, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
