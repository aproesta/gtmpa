'use strict';

angular.module('gtmpaApp')
    .factory('Planmilestone', function ($resource, DateUtils) {
        return $resource('api/planmilestones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.milestoneDate = DateUtils.convertLocaleDateFromServer(data.milestoneDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.milestoneDate = DateUtils.convertLocaleDateToServer(data.milestoneDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.milestoneDate = DateUtils.convertLocaleDateToServer(data.milestoneDate);
                    return angular.toJson(data);
                }
            }
        });
    });
