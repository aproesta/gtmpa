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
            $scope.alertMessage = (date.title + ' was clicked ');
        };
        /* alert on Drop */
        $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
            $scope.alertMessage = ('Event Droped to make dayDelta ' + delta);
        };
        /* alert on Resize */
        $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
            $scope.alertMessage = ('Event Resized to make dayDelta ' + delta);
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
            {title: 'Initial Workshop',start: new Date(y, m, 2), allDay: true},
            {title: 'Sales Competency',start: new Date(y, m, 7), allDay: true},
            {title: 'PreSales Competency',start: new Date(y, m, 16), allDay: true},
            {title: 'Technical Competency',start: new Date(y, m, 21), allDay: true},
            {title: 'Solution Architecture',start: new Date(y, m +1 , 2), allDay: true},
            {title: 'Solution Collateral', start: new Date(y, m + 1, 7), allDay: true}
        ];

        $scope.eventSources = [$scope.events];

    });
