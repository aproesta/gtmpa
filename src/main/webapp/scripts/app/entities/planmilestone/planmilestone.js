'use strict';

angular.module('gtmpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('planmilestone', {
                parent: 'entity',
                url: '/planmilestones',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Planmilestones'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/planmilestone/planmilestones.html',
                        controller: 'PlanmilestoneController'
                    }
                },
                resolve: {
                }
            })
            .state('planmilestone.detail', {
                parent: 'entity',
                url: '/planmilestone/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Planmilestone'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/planmilestone/planmilestone-detail.html',
                        controller: 'PlanmilestoneDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Planmilestone', function($stateParams, Planmilestone) {
                        return Planmilestone.get({id : $stateParams.id});
                    }]
                }
            })
            .state('planmilestone.new', {
                parent: 'planmilestone',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/planmilestone/planmilestone-dialog.html',
                        controller: 'PlanmilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    milestoneDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('planmilestone', null, { reload: true });
                    }, function() {
                        $state.go('planmilestone');
                    })
                }]
            })
            .state('planmilestone.edit', {
                parent: 'planmilestone',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/planmilestone/planmilestone-dialog.html',
                        controller: 'PlanmilestoneDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Planmilestone', function(Planmilestone) {
                                return Planmilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('planmilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('planmilestone.delete', {
                parent: 'planmilestone',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/planmilestone/planmilestone-delete-dialog.html',
                        controller: 'PlanmilestoneDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Planmilestone', function(Planmilestone) {
                                return Planmilestone.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('planmilestone', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
