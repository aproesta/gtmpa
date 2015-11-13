'use strict';

angular.module('gtmpaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


