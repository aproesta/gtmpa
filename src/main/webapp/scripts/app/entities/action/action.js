'use strict';


angular.module('gtmpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('action', {
                parent: 'entity',
                url: '/actions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Actions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/action/actions.html',
                        controller: 'ActionController'
                    }
                },
                resolve: {
                }
            })
            .state('action.detail', {
                parent: 'entity',
                url: '/action/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Action'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/action/action-detail.html',
                        controller: 'ActionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Action', function($stateParams, Action) {
                        return Action.get({id : $stateParams.id});
                    }]
                }
            })
            .state('action.new', {
                parent: 'action',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                	
                    $modal.open({
                        templateUrl: 'scripts/app/entities/action/action-dialog.html',
                        controller: 'ActionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    logDate: null,
                                    description: null,
                                    actionItems: null,
                                    nextActionDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('action', null, { reload: true });
                    }, function() {
                        $state.go('action');
                    })
                }]
            })
            .state('action.edit', {
                parent: 'action',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/action/action-dialog.html',
                        controller: 'ActionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Action', function(Action) {
                                return Action.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('action', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('action.complete', {
                parent: 'action',
                url: '/{id}/complete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                	window.alert("Mark this action complete: " + $stateParams.id);
                	$state.go('action', null, { reload: true });
                }]
            })
            .state('action.delete', {
                parent: 'action',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/action/action-delete-dialog.html',
                        controller: 'ActionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Action', function(Action) {
                                return Action.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('action', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

