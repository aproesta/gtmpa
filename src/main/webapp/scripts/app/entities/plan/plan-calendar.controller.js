'use strict';

angular.module('gtmpaApp')
    .controller('PlanCalendarController', function ($scope, $rootScope, $stateParams, entity, Plan, Partner, $confirm) {
        $scope.plan = entity;
        
        
        $scope.load = function (id) {
            Plan.get({id: id}, function(result) {
                $scope.plan = result;
            });
        };

        var $events = [
	                {title: 'Initial Discussion',start: $scope.plan.initialDiscussionDate, allDay: true, color: 'red'},
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

        $scope.loadEvents = function(start, end, timezone, callback) {
            Plan.get({id: $stateParams.id}, function(result) {
                $scope.plan = result;


                callback($events);
                // window.setTimeout($scope.setCalendarStart, 500);
            });
        };
        
        var unsubscribe = $rootScope.$on('gtmpaApp:planUpdate', function(event, result) {
            $scope.plan = result;
        });
        $scope.$on('$destroy', unsubscribe);

        /* alert on eventClick */
        $scope.alertOnDrop = function(event, jsEvent, view ){
        	$confirm({text: 'Are you sure you wish to move this milestone?'})
            	.then(function() {
            		for (var i = 1; i < $events.length; i++)
            			{	
            				var eventDate = new Date($events[i].start);
            				console.log(eventDate);
            				eventDate.setDate(eventDate.getDate() + 3);
            				$events[i].start = eventDate;
            			}
            		$('#calendar').fullCalendar( 'refetchEvents' );
            });
        };
        /* alert on Drop */
        $scope.alertOnEventClick = function(event, delta, revertFunc, jsEvent, ui, view){
        	$confirm({text: 'Mark this milestone as complete? This plan will progress to the Sales Competency state.'})
        	.then(function() {
        	$events[0].color  = 'green';
        	$events[1].color  = 'red';
        	$('#calendar').fullCalendar( 'refetchEvents' );
        });
        };
        /* alert on Resize */
        $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
        	console.log('Event Resized to make dayDelta ' + delta);
        };

 
        $scope.setCalendarStart = function () {
        	$('#calendar').fullCalendar('gotoDate', $scope.plan.initialDiscussionDate);
        };


        $('#calendar').fullCalendar({
            height: 450,
            editable: true,
            eventClick: $scope.alertOnEventClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize,
            events: $scope.loadEvents 
        });

    });
