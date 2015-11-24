'use strict';

angular.module('gtmpaApp')
    .controller('ActionController', function ($scope, $state, $modal, Action, ParseLinks) {
      
        $scope.actions = [];
        $scope.currentDate = new Date();
        $scope.currentDate.setHours(0, 0, 0, 0);
        //window.alert("IAM ERE: " + $scope.currentDate);
        $scope.page = 0;
        $scope.loadAll = function() {
            Action.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.actions = result;
               //Naresh Dimari this might interfare with creation of date widget in Action create page
                angular.forEach( $scope.actions, function(action) {
                	//window.alert("IAM ERE: " + action.nextActionDate); action.status=action.status+'overdue';
              	  if((Date.parse(action.nextActionDate)) < $scope.currentDate.getTime()) {
              		action.status=action.status+'overdue';
              	  }
              	});
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
                id: null,
                status: null
            };
        };
    });
