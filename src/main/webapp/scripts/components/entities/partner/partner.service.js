'use strict';

angular.module('gtmpaApp')
    .factory('Partner', function ($resource, DateUtils) {
        return $resource('api/partners/:id', {}, {
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
