'use strict';

angular.module('gtmpaApp')
    .factory('Action', function ($resource, DateUtils) {
        return $resource('api/actions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.logDate = DateUtils.convertLocaleDateFromServer(data.logDate);
                    data.nextActionDate = DateUtils.convertLocaleDateFromServer(data.nextActionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.logDate = DateUtils.convertLocaleDateToServer(data.logDate);
                    data.nextActionDate = DateUtils.convertLocaleDateToServer(data.nextActionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.logDate = DateUtils.convertLocaleDateToServer(data.logDate);
                    data.nextActionDate = DateUtils.convertLocaleDateToServer(data.nextActionDate);
                    return angular.toJson(data);
                }
            }
        });
    });
