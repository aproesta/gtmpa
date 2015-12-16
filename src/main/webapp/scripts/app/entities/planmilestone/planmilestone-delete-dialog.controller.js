'use strict';

angular.module('gtmpaApp')
	.controller('PlanmilestoneDeleteController', function($scope, $modalInstance, entity, Planmilestone) {

        $scope.planmilestone = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Planmilestone.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });