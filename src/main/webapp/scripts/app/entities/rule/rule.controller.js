'use strict';

angular.module('gtmpaApp')
    .controller('RuleController', function ($scope, $state, $modal, Rule, ParseLinks) {
      
        $scope.rules = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Rule.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.rules = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.rule = {
                name: null,
                fieldSpec: null,
                rule: null,
                id: null
            };
        };
    });
