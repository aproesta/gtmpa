 'use strict';

angular.module('gtmpaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-gtmpaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-gtmpaApp-params')});
                }
                return response;
            }
        };
    });
