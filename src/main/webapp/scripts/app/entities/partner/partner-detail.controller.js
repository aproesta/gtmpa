'use strict';

angular.module('gtmpaApp')
    .controller('PartnerDetailController', function ($scope, $rootScope, $stateParams, entity, Partner) {
        $scope.partner = entity;
        $scope.load = function (id) {
            Partner.get({id: id}, function(result) {
                $scope.partner = result;
            });
        };
        var unsubscribe = $rootScope.$on('gtmpaApp:partnerUpdate', function(event, result) {
            $scope.partner = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
