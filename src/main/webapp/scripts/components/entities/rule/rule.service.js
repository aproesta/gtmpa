'use strict';

angular.module('gtmpaApp')
    .factory('Rule', function ($resource, DateUtils) {
        return $resource('api/rules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
