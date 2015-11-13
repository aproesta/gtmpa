'use strict';

angular.module('gtmpaApp')
    .factory('Plan', function ($resource, DateUtils) {
        return $resource('api/plans/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.agreedGTMDate = DateUtils.convertLocaleDateFromServer(data.agreedGTMDate);
                    data.proposalDate = DateUtils.convertLocaleDateFromServer(data.proposalDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.agreedGTMDate = DateUtils.convertLocaleDateToServer(data.agreedGTMDate);
                    data.proposalDate = DateUtils.convertLocaleDateToServer(data.proposalDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.agreedGTMDate = DateUtils.convertLocaleDateToServer(data.agreedGTMDate);
                    data.proposalDate = DateUtils.convertLocaleDateToServer(data.proposalDate);
                    return angular.toJson(data);
                }
            }
        });
    });
