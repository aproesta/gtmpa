'use strict';

angular.module('gtmpaApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            
            $scope.c1_labels = ["Q1", "Q2", "Q3", "Q4"];
            $scope.c1_series = ['Cloud', 'Analytics', 'Mobile', 'Security', 'Social'];
            $scope.c1_data = [
              [25, 50, 55, 65],
              [80, 70, 70, 40],
              [65, 59, 80, 81],
              [15, 20, 45, 45],
              [28, 48, 40, 19]
            ];
            
            $scope.c2_labels = ['2013', '2014', '2015'];
            $scope.c2_series = ['VIC', 'NSW', 'SA', 'WA', 'ACT', 'NT', 'TAS'];

            $scope.c2_data = [
              [30, 59, 80, 11, 66],
              [35, 48, 40, 19, 46],
              [45, 59, 50, 21, 36],
              [30, 65, 30, 39, 26],
              [60, 65, 60, 31, 46],
              [15, 70, 50, 49, 66],
              [75, 59, 40, 31, 66]
            ];
            
            $scope.c3_labels = ["Banking", "Health", "Government", "Manufacturing", "Transportation", "Retail"];
            $scope.c3_data = [30, 50, 70, 100, 20, 79];
            
        });
    });
