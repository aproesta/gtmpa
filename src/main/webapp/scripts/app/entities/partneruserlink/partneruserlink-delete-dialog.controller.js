'use strict';

angular.module('gtmpaApp')
	.controller('PartneruserlinkDeleteController', function($scope, $modalInstance, entity, Partneruserlink) {

        $scope.partneruserlink = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Partneruserlink.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });