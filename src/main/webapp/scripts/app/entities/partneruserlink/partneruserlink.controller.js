'use strict';

angular.module('gtmpaApp')
    .controller('PartneruserlinkController', function ($scope, $state, $modal, Partneruserlink) {
      
        $scope.partneruserlinks = [];
        
        $scope.loadAll = function() {
            Partneruserlink.query(function(result) {
               $scope.partneruserlinks = result;              
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.partneruserlink = {
                description: null,
                id: null
            };
        };
    });
