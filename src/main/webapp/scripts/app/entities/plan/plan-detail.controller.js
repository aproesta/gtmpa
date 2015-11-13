'use strict';

angular.module('gtmpaApp')
    .controller('PlanDetailController', function ($scope, $rootScope, $stateParams, entity, Plan, Partner) {
        $scope.plan = entity;
        $scope.load = function (id) {
            Plan.get({id: id}, function(result) {
                $scope.plan = result;
            });
        };
        var unsubscribe = $rootScope.$on('gtmpaApp:planUpdate', function(event, result) {
            $scope.plan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
