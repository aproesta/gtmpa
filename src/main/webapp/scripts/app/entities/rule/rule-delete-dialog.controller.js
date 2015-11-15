'use strict';

angular.module('gtmpaApp')
	.controller('RuleDeleteController', function($scope, $modalInstance, entity, Rule) {

        $scope.rule = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Rule.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });