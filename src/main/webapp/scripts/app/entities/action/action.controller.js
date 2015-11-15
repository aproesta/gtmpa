'use strict';

angular.module('gtmpaApp')
    .controller('ActionController', function ($scope, $state, $modal, Action, ParseLinks) {
      
        $scope.actions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Action.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.actions = result;
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
            $scope.action = {
                logDate: null,
                description: null,
                actionItems: null,
                nextActionDate: null,
                id: null
            };
        };
    });
