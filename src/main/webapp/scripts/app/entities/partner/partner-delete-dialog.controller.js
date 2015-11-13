'use strict';

angular.module('gtmpaApp')
	.controller('PartnerDeleteController', function($scope, $modalInstance, entity, Partner) {

        $scope.partner = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Partner.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });