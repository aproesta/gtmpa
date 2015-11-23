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

        /* alert on eventClick */
        $scope.alertOnEventClick = function( date, jsEvent, view){
        	$('#calendar').fullCalendar('gotoDate', $scope.plan.initialDiscussionDate);
            console.log(date.title + ' was clicked ');
        };
        /* alert on Drop */
        $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
        	console.log('Event ' + event.start + '  Droped to make dayDelta ' + delta);
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
                    center: 'title',
                    right: 'today prev,next'
                },
                dayClick: $scope.alertOnEventClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize
            }
        };
        

        $scope.setCalendarStart = function () {
        	$('#calendar').fullCalendar('gotoDate', $scope.plan.initialDiscussionDate);
        };


        $('#calendar').fullCalendar({
            height: 450,
            editable: true,
            dayClick: $scope.alertOnEventClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize,
            events: function(start, end, timezone, callback) {
                Plan.get({id: $stateParams.id}, function(result) {
                    $scope.plan = result;
                    
                    var events = [
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
                    callback(events);
                    window.setTimeout($scope.setCalendarStart, 500);
                });
            }
        });

    });
