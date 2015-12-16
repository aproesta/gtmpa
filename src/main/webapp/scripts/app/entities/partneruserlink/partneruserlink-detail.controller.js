'use strict';

angular.module('gtmpaApp')
    .controller('PartneruserlinkDetailController', function ($scope, $rootScope, $stateParams, entity, Partneruserlink) {
        $scope.partneruserlink = entity;
        $scope.load = function (id) {
            Partneruserlink.get({id: id}, function(result) {
                $scope.partneruserlink = result;
            });
        };
        var unsubscribe = $rootScope.$on('gtmpaApp:partneruserlinkUpdate', function(event, result) {
            $scope.partneruserlink = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
