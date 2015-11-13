'use strict';

angular.module('gtmpaApp')
    .controller('PartnerController', function ($scope, $state, $modal, Partner, ParseLinks) {
      
        $scope.partners = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Partner.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.partners = result;
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
            $scope.partner = {
                name: null,
                id: null
            };
        };
    });
