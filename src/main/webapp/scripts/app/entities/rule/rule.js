'use strict';

angular.module('gtmpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rule', {
                parent: 'entity',
                url: '/rules',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_BUS_OPS'],
                    pageTitle: 'Rules'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rule/rules.html',
                        controller: 'RuleController'
                    }
                },
                resolve: {
                }
            })
            .state('rule.detail', {
                parent: 'entity',
                url: '/rule/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_BUS_OPS'],
                    pageTitle: 'Rule'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rule/rule-detail.html',
                        controller: 'RuleDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Rule', function($stateParams, Rule) {
                        return Rule.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rule.new', {
                parent: 'rule',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_BUS_OPS'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rule/rule-dialog.html',
                        controller: 'RuleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    fieldSpec: null,
                                    rule: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rule', null, { reload: true });
                    }, function() {
                        $state.go('rule');
                    })
                }]
            })
            .state('rule.edit', {
                parent: 'rule',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_BUS_OPS'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rule/rule-dialog.html',
                        controller: 'RuleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Rule', function(Rule) {
                                return Rule.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rule', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rule.delete', {
                parent: 'rule',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rule/rule-delete-dialog.html',
                        controller: 'RuleDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Rule', function(Rule) {
                                return Rule.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rule', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
