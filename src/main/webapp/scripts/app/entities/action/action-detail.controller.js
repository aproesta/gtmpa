'use strict';

angular.module('gtmpaApp')
    .controller('ActionDetailController', function ($scope, $rootScope, $stateParams, entity, Action, Plan) {
        $scope.action = entity;
        $scope.load = function (id) {
            Action.get({id: id}, function(result) {
                $scope.action = result;
            });
        };
        var unsubscribe = $rootScope.$on('gtmpaApp:actionUpdate', function(event, result) {
            $scope.action = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
