'use strict';

angular.module('gtmpaApp')
    .controller('PlanController', function ($scope, $state, $modal, Plan, ParseLinks) {
        $scope.plans = [];
        
        $scope.page = 0;
        $scope.loadAll = function() {
            Plan.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.plans = result;
               
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
        
        $scope.printMeOnSelect = function (plan) {
        	//alert('IAMMMMM  about to EMIT');
        	//alert('IAMMMMM  Partner Name : ' + plan.partner.name); 
        	 $scope.$emit('gtmpaApp:toUserController', plan.partner);
        	// alert('************* EMIT');
        	 //console.log('****************logging**************',plan.partner);
          
        };

        $scope.clear = function () {
            $scope.plan = {
                solutionName: null,
                agreedGTMDate: null,
                revenueCommitment: null,
                dealsRequired: null,
                proposalDate: null,
                industrySegment: null,
                id: null
            };
        };
    });
	
