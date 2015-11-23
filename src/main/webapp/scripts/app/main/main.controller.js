'use strict';

angular.module('gtmpaApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
            
            $scope.c1_labels = ["January", "February", "March", "April", "May", "June", "July"];
            $scope.c1_series = ['Partner A', 'Partner B'];
            $scope.c1_data = [
              [65, 59, 80, 81, 56, 55, 40],
              [28, 48, 40, 19, 86, 27, 90]
            ];
            $scope.c1_onClick = function (points, evt) {
              console.log(points, evt);
            };
            
            $scope.c2_labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
            $scope.c2_series = ['Partner A', 'Partner B'];

            $scope.c2_data = [
              [65, 59, 80, 81, 56, 55, 40],
              [28, 48, 40, 19, 86, 27, 90]
            ];
            
            $scope.c3_labels = ["Health", "Government", "Manufacturing"];
            $scope.c3_data = [300, 500, 100];
            
        });
    });
