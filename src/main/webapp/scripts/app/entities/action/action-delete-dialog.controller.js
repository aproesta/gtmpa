'use strict';

angular.module('gtmpaApp')
	.controller('ActionDeleteController', function($scope, $modalInstance, entity, Action) {

        $scope.action = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Action.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });