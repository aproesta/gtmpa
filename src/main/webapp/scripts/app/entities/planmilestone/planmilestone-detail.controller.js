'use strict';

angular.module('gtmpaApp')
    .controller('PlanmilestoneDetailController', function ($scope, $rootScope, $stateParams, entity, Planmilestone, Plan) {
        $scope.planmilestone = entity;
        
        
        $scope.load = function (id) {
        	 
            Planmilestone.get({id: id}, function(result) {
            	
                $scope.planmilestone = result;
               
                $scope.planmilestone.milestoneType = $scope.planmilestone.milestoneType.replace('Milestone','');
            });
          };
        
        var unsubscribe = $rootScope.$on('gtmpaApp:planmilestoneUpdate', function(event, result) {
            $scope.planmilestone = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
