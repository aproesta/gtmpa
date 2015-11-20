'use strict';

angular.module('gtmpaApp')
    .controller('PlanCalendarController', function ($scope, $rootScope, $stateParams, entity, Plan, Partner) {
        $scope.plan = entity;
        $scope.load = function (id) {
            Plan.get({id: id}, function(result) {
                $scope.plan = result;
            });
        };
        var unsubscribe = $rootScope.$on('gtmpaApp:planUpdate', function(event, result) {
            $scope.plan = result;
        });
        $scope.$on('$destroy', unsubscribe);

        /**
         * Calendar Events
         */
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        /* alert on eventClick */
        $scope.alertOnEventClick = function( date, jsEvent, view){
            console.log(date.title + ' was clicked ');
        };
        /* alert on Drop */
        $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
        	console.log('Event Droped to make dayDelta ' + delta);
        };
        /* alert on Resize */
        $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
        	console.log('Event Resized to make dayDelta ' + delta);
        };

        /* calendar config object */
        $scope.uiConfig = {
            calendar:{
                height: 450,
                editable: true,
                header:{
                    left: 'title',
                    center: '',
                    right: 'today prev,next'
                },
                dayClick: $scope.alertOnEventClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize
            }
        };

        /* Calendar Data */
        /* event source that contains custom events on the scope */
        $scope.events = [
            {title: 'Initial Discussion',start: $scope.plan.initialDiscussionDate, allDay: true},
            {title: 'Sales Competency',start: $scope.plan.salesCompetencyDate, allDay: true},
            {title: 'PreSales Competency',start: $scope.plan.preSalesCompetencyDate, allDay: true},
            {title: 'Technical Competency',start: $scope.plan.technicalCompetencyDate, allDay: true},
            {title: 'Solution Architecture',start: $scope.plan.solutionArchitectedDate, allDay: true},
            {title: 'Solution Costed', start: $scope.plan.solutionCostedDate, allDay: true},
            {title: 'Solution Collateral', start: $scope.plan.solutionCollateralDate, allDay: true},
            {title: 'Marketing Collateral', start: $scope.plan.marketingCollateralDate, allDay: true},
            {title: 'Marketing Plan', start: $scope.plan.marketingPlanDate, allDay: true},
            {title: 'Campaign Plan', start: $scope.plan.campaignPlanDate, allDay: true},
            {title: 'Complete', start: $scope.plan.completeDate, allDay: true}
        ];

        $scope.eventSources = [$scope.events];

    });
