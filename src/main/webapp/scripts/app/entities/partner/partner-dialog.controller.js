'use strict';

angular.module('gtmpaApp').controller('PartnerDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Partner',
        function($scope, $stateParams, $modalInstance, entity, Partner) {

        $scope.partner = entity;
        $scope.load = function(id) {
            Partner.get({id : id}, function(result) {
                $scope.partner = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gtmpaApp:partnerUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.partner.id != null) {
                Partner.update($scope.partner, onSaveSuccess, onSaveError);
            } else {
                Partner.save($scope.partner, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
