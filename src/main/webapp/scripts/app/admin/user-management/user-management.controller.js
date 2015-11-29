'use strict';

angular.module('gtmpaApp')
    .controller('UserManagementController', function ($scope,$rootScope, User, ParseLinks, Partneruserlink) {
	
    	
        $scope.users = [];
        $scope.usersForPartner = [];
        $scope.partnerid;
        $scope.partneruserlinks = [];//;  
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN", "ROLE_BP", "ROLE_BP_MANAGER", "ROLE_CHANNEL_MANAGER", "ROLE_BUS_OPS"];
        
        $scope.page = 0;
        $scope.loadAll = function () {
        	//alert('UserManagementController ' ); 
            User.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.users = result;
            });
            Partneruserlink.query({page: $scope.page, per_page: 20}, function (result1, headers) {
                //$scope.links = ParseLinks.parse(headers('link'));
                $scope.partneruserlinks = result1;
            });
           
        };

    	
        $scope.loadPage = function (page) {
        	 //alert('IAMMMMM');
            $scope.page = page;
            $scope.loadAll();
        };
        
        //naresh this is listening here from plan.controller.js
        $rootScope.$on('gtmpaApp:toUserController', function(event, partner) {
        	
    		//alert('I am LISTENING  : ' + partner.name); 
    		if(null != partner){
    		
    			console.log('number of users ******************* :' + $scope.users.length);
    			//$scope.users=$scope.users.slice(0,3);
    			//console.log('NOW number of users ******************* :' + $scope.users.length);
    			angular.forEach($scope.partneruserlinks, function(partneruserlink) {
                	
              	  if(partneruserlink.partner.id == partner.id) {
              		//$scope.usersForPartner.a;
              		angular.forEach($scope.users, function(user) {
                    	
                    	  if(user.id == partneruserlink.user.id) {
                    		  $scope.usersForPartner.push(user);
                    	  }
                    	});
              	  }
              	});
    			
    			$scope.users = $scope.usersForPartner;
    			console.log('NOW number of users ******************* :' + $scope.usersForPartner.length);
    		}
    	   		
        });
        
        $scope.loadAll();

        $scope.setActive = function (user, isActivated) {
        	 
            user.activated = isActivated;
            User.update(user, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };
        
        $scope.clear = function () {
        	 
            $scope.user = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });