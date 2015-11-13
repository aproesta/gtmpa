'use strict';

angular.module('gtmpaApp')
	.controller('PlanDeleteController', function($scope, $modalInstance, entity, Plan) {

        $scope.plan = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Plan.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });