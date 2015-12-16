'use strict';

angular.module('gtmpaApp')
    .controller('PlanmilestoneController', function ($scope, $state, $modal, Planmilestone, ParseLinks) {
      
        $scope.planmilestones = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Planmilestone.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.planmilestones = result;
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
            $scope.planmilestone = {
                milestoneDate: null,
                id: null
            };
        };
    });
