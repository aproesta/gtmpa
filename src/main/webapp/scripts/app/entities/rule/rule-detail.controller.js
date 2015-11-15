'use strict';

angular.module('gtmpaApp')
    .controller('RuleDetailController', function ($scope, $rootScope, $stateParams, entity, Rule) {
        $scope.rule = entity;
        $scope.load = function (id) {
            Rule.get({id: id}, function(result) {
                $scope.rule = result;
            });
        };
        var unsubscribe = $rootScope.$on('gtmpaApp:ruleUpdate', function(event, result) {
            $scope.rule = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
